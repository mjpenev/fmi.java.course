package bg.sofia.uni.fmi.mjt.poll.server.command;


import bg.sofia.uni.fmi.mjt.poll.server.repository.PollRepository;

public class CommandExecutor {
    private static final String INVALID_ARGS_COUNT_MESSAGE_FORMAT =
            "Invalid count of arguments: \"%s\" expects %d arguments. Example: \"%s\"";

    private static final String CREATE = "create-poll";
    private static final String LIST = "list-polls";
    private static final String VOTE = "submit-vote";
    private static final String DISCONNECT = "disconnect";

    private PollRepository repository;

    public CommandExecutor(PollRepository repository) {
        this.repository = repository;
    }

    public String execute(Command cmd) {
        return switch (cmd.command()) {
            case CREATE -> createPoll(cmd.arguments());
            case LIST -> listPolls();
            case VOTE -> submitVote(cmd.arguments());
            case DISCONNECT -> disconnect();
            default -> "Unknown command";
        };
    }

    private String createPoll(String[] arguments) {
        if (arguments.length < 3) {
            return String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, CREATE, 3, CREATE + " <question> <option1> <option2>");
        }
        return null;
    }

    private String listPolls() {

        return null;
    }

    private String submitVote(String[] arguments) {
        if (arguments.length != 2) {
            return String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, VOTE, 2, VOTE + " <poll-id> <option> ");
        }
        return null;
    }

    private String disconnect() {
        return null;
    }


}