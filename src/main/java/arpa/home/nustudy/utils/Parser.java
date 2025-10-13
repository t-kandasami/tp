package arpa.home.nustudy.utils;

import arpa.home.nustudy.command.*;
import arpa.home.nustudy.exceptions.NUStudyCommandException;

public enum Parser {
    ;
    private static ResetCourseHoursCommand resetCourseHoursCommand;

    /**
     * Returns a Command parsed from user's input
     *
     * @param input The user-inputted command string
     * @return A Command object that can execute the user's request
     * @throws NUStudyCommandException If the command is invalid
     */
    public static Command parseCommand(final String input) throws NUStudyCommandException {
        if (input == null || input.trim().isEmpty()) {
            throw new NUStudyCommandException("Input cannot be empty");
        }

        final String[] words = input.split("\\s+", 2);
        final String command = words[0].toLowerCase();
        final String arguments = words.length > 1 ? words[1].trim() : "";

        switch (command) {
            case "add":
                return Parser.parseAddCommand(arguments);
            case "list":
                return new ListCourseCommand(arguments);
            case "reset":
                return new ResetCourseHoursCommand(arguments);
            case "delete":
                return new DeleteCourseCommand(arguments);
            case "exit":
                return new ExitCommand();
            default:
                throw new NUStudyCommandException("Wrong command");
        }
    }

    /**
     * Parses add commands to determine whether to add a course or session.
     *
     * @param arguments The arguments following the "add" command
     * @return Either AddCourseCommand or AddSessionCommand
     * @throws NUStudyCommandException If the arguments are invalid
     */
    private static Command parseAddCommand(final String arguments) throws NUStudyCommandException {
        if (arguments.isEmpty()) {
            throw new NUStudyCommandException("Add command requires arguments. " +
                    "Usage: add <course> OR add <course> <session>");
        }

        final String[] parts = arguments.split("\\s+");

        // If there's exactly one word, it's a course
        if (parts.length == 1) {
            return new AddCourseCommand(arguments);
        }
        // If there are two or more words, treat as course + session
        else if (parts.length >= 2) {
            return new AddSessionCommand(arguments);
        } else {
            throw new NUStudyCommandException("Invalid add command format. " +
                    "Usage: add <course> OR add <course> <session>");
        }
    }
}
