import bg.sofia.uni.fmi.mjt.instagramFollowersChecker.Reader.FileParser;
import bg.sofia.uni.fmi.mjt.sentimentanalyzer.AnalyzerInput;
import bg.sofia.uni.fmi.mjt.sentimentanalyzer.ParallelSentimentAnalyzer;
import bg.sofia.uni.fmi.mjt.sentimentanalyzer.SentimentScore;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

class Main {
    public static void main(String[] args) {
        AnalyzerInput input1 = new AnalyzerInput("doc1", new StringReader("I love java"));
        AnalyzerInput input2 = new AnalyzerInput("doc2", new StringReader("I hate bugs"));
        Reader stopWordsReader = null;
        try {
            stopWordsReader = new BufferedReader(new FileReader("stopwords.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ParallelSentimentAnalyzer analyzer;
        try (Reader lexiconReader = new BufferedReader(new FileReader("AFINN-111.txt"))) {
            analyzer = new ParallelSentimentAnalyzer(4, stopWordsReader, lexiconReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, SentimentScore> results = analyzer.analyze(input1, input2);

        for (Map.Entry<String, SentimentScore> resultEntry : results.entrySet()) {
            System.out.println("======================");
            System.out.println(resultEntry.getKey());
            System.out.println(resultEntry.getValue());
            System.out.println("======================");
        }
    }
}