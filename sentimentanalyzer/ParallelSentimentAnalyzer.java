package bg.sofia.uni.fmi.mjt.sentimentanalyzer;

import bg.sofia.uni.fmi.mjt.sentimentanalyzer.exceptions.SentimentAnalysisException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ParallelSentimentAnalyzer implements SentimentAnalyzerAPI {

    private final int workersCount;
    private final Set<String> stopWords;
    private final Map<String, SentimentScore> sentimentLexicon;
    private static final AnalyzerInput POISON_PILL =
            new AnalyzerInput("__POISON__", Reader.nullReader());

    public ParallelSentimentAnalyzer(int workersCount, Reader stopWordsReader, Reader lexiconReader) {
        this.workersCount = workersCount;
        this.stopWords = loadStopWords(stopWordsReader);
        this.sentimentLexicon = loadLexicon(lexiconReader);
    }
    private Set<String> loadStopWords(Reader reader) {
        try (BufferedReader br = new BufferedReader(reader)) {
            return br.lines()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Map<String, SentimentScore> loadLexicon(Reader reader) {
        try (BufferedReader br = new BufferedReader(reader)) {
            return br.lines()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(line -> line.split("\\s+"))
                    .collect(Collectors.toMap(
                            parts -> String.join(" ", Arrays.copyOf(parts, parts.length - 1)),
                            parts -> SentimentScore.fromScore(Integer.parseInt(parts[parts.length - 1]))
                    ));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }



    private static class Producer implements Runnable {
        private final BlockingQueue<AnalyzerInput> blockingQueue;
        private final AnalyzerInput input;

        public Producer(BlockingQueue<AnalyzerInput> blockingQueue, AnalyzerInput input) {
            this.blockingQueue = blockingQueue;
            this.input = input;
        }

        @Override
        public void run() {
            try {
                blockingQueue.put(input);
            } catch (InterruptedException e) {
                throw new SentimentAnalysisException("Producer was interrupted", e);
            }
        }
    }

    private class Consumer implements Runnable {
        private final BlockingQueue<AnalyzerInput> blockingQueue;
        private final Map<String, SentimentScore> results;

        public Consumer(BlockingQueue<AnalyzerInput> blockingQueue, Map<String, SentimentScore> results) {
            this.blockingQueue = blockingQueue;
            this.results = results;
        }

        private String readAll(Reader reader) {
            try (BufferedReader br = new BufferedReader(reader)) {
                return br.lines().collect(Collectors.joining(" "));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        private SentimentScore analyzeText(String text) {
            StringBuilder modifiedText = new StringBuilder();
            for (char c : text.toCharArray()) {
                if (Character.isLetter(c) || Character.isWhitespace(c)) {
                    modifiedText.append(c);
                } else {
                    modifiedText.append(' ');
                }
            }

            String[] words = modifiedText.toString().toLowerCase().split("\\s+");

            var avg = Arrays.stream(words)
                    .filter(word -> !stopWords.contains(word))
                    .map(sentimentLexicon::get)
                    .filter(Objects::nonNull)
                    .mapToInt(SentimentScore::getScore)
                    .average()
                    .orElse(0.0);

            int scoreToInt = (int) Math.round(avg);

            return SentimentScore.fromScore(scoreToInt);
        }

        @Override
        public void run() {
            try {
                while (true) {
                    AnalyzerInput task = blockingQueue.take();
                    if (task == POISON_PILL) {
                        break;
                    }
                    String text = readAll(task.inputReader());

                    SentimentScore score = analyzeText(text);
                    results.put(task.inputID(), score);
                }
            } catch (InterruptedException e) {
                throw new SentimentAnalysisException("Consumer was interrupted");
            }
        }
    }


    @Override
    public Map<String, SentimentScore> analyze(AnalyzerInput... input) {
        if (input == null) {
            throw new IllegalArgumentException("Input argument shall not be null");
        }

        BlockingQueue<AnalyzerInput> blockingQueue = new ArrayBlockingQueue<>(input.length);
        Map<String, SentimentScore> result = new ConcurrentHashMap<>();
        List<Thread> producerThreads = new ArrayList<>();
        List<Thread> consumerThreads = new ArrayList<>();


        for (int i = 0; i < this.workersCount; i++) {
            Consumer consumerRunnable = new Consumer(blockingQueue, result);
            Thread consumerThread = new Thread(consumerRunnable);
            consumerThreads.add(consumerThread);
            consumerThread.start();
        }

        for (AnalyzerInput analyzerInput : input) {
            Producer producerRunnable = new Producer(blockingQueue, analyzerInput);
            Thread producerThread = new Thread(producerRunnable);
            producerThreads.add(producerThread);
            producerThread.start();
        }

        for (Thread t : producerThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        for (int i = 0; i < workersCount; i++) {
            try{
                blockingQueue.put(POISON_PILL);
            } catch (InterruptedException e) {
                throw new SentimentAnalysisException("Couldn't load the poison pills.",e);
            }
        }

        for (Thread t : consumerThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return result;
    }
}
