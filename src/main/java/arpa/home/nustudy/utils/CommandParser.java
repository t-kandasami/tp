package arpa.home.nustudy.utils;

import arpa.home.nustudy.command.AddCourseCommand;
import arpa.home.nustudy.command.AddSessionCommand;
import arpa.home.nustudy.command.Command;
import arpa.home.nustudy.command.DeleteCourseCommand;
import arpa.home.nustudy.command.DeleteSessionByDateCommand;
import arpa.home.nustudy.command.DeleteSessionByIndexCommand;
import arpa.home.nustudy.command.EditCourseNameCommand;
import arpa.home.nustudy.command.EditSessionCommand;
import arpa.home.nustudy.command.ExitCommand;
import arpa.home.nustudy.command.FilterByDateCommand;
import arpa.home.nustudy.command.FilterByNameAndDateCommand;
import arpa.home.nustudy.command.FilterByNameCommand;
import arpa.home.nustudy.command.HelpCommand;
import arpa.home.nustudy.command.ListCourseCommand;
import arpa.home.nustudy.command.ListCourseHoursPerSessionCommand;
import arpa.home.nustudy.command.ResetCourseHoursCommand;
import arpa.home.nustudy.exceptions.NUStudyCommandException;

/**
 * Parses user input strings into Command objects.
 */
public class CommandParser {
    private static ResetCourseHoursCommand resetCourseHoursCommand;

    /**
     * Returns a Command parsed from user's input
     *
     * @param input The user-inputted command string
     *
     * @return A Command object that can execute the user's request
     *
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
            return parseAddCommand(arguments);
        case "list":
            return parseListCommand(arguments);
        case "reset":
            return new ResetCourseHoursCommand(arguments);
        case "edit":
            return parseEditCommand(arguments);
        case "delete":
            return parseDeleteCommand(arguments);
        case "exit":
            // Only accept "exit" with no extra arguments
            if (!arguments.isEmpty()) {
                throw new NUStudyCommandException("Invalid exit command format. Usage: exit");
            }
            return new ExitCommand();
        case "filter":
            return parseFilterCommand(arguments);
        case "help":
            return new HelpCommand();
        default:
            throw new NUStudyCommandException("Wrong command");
        }
    }

    /**
     * Parses add commands to determine whether to add a course or session.
     *
     * @param arguments The arguments following the "add" command
     *
     * @return Either AddCourseCommand or AddSessionCommand
     *
     * @throws NUStudyCommandException If the arguments are invalid
     */
    private static Command parseAddCommand(final String arguments) throws NUStudyCommandException {
        if (arguments.isEmpty()) {
            throw new NUStudyCommandException("""
                    Add command requires arguments.
                    Usage: add <course> OR add <course> <hours> OR add <course> <hours> <date>""");
        }

        final String[] parts = arguments.split("\\s+");

        if (parts.length == 1) {  // If there's exactly one word, it's a course
            return new AddCourseCommand(arguments);
        } else if (parts.length == 2 || parts.length == 3) {  // If there are two or more words, treat as course +
            // session or course + session + Date
            return new AddSessionCommand(arguments);
        } else {
            throw new NUStudyCommandException("""
                    Invalid add command format.
                    Usage: add <course> OR add <course> <hours> OR add <course> <hours> <date>""");
        }
    }

    /**
     * Parse list commands to determine whether to list courses or course hours per session
     *
     * @param arguments The arguments following the "list" command
     *
     * @return Either {@link ListCourseCommand} or {@link ListCourseHoursPerSessionCommand}
     *
     * @throws NUStudyCommandException If the arguments are invalid
     */
    private static Command parseListCommand(final String arguments) throws NUStudyCommandException {
        if (arguments.isEmpty()) {
            return new ListCourseCommand();
        }

        final String[] parts = arguments.split("\\s+");

        if (parts.length == 1) {  // If there's exactly one word, it's a course
            return new ListCourseHoursPerSessionCommand(arguments);
        } else {
            throw new NUStudyCommandException("""
                    Invalid list command format.
                    Usage: list OR list <course>""");
        }
    }

    /**
     * Parse edit commands to determine whether to edit course or session
     *
     * @param arguments The arguments following the "edit" command
     *
     * @return {@link EditSessionCommand}
     *
     * @throws NUStudyCommandException If the arguments are invalid
     */
    private static Command parseEditCommand(final String arguments) throws NUStudyCommandException {
        if (arguments.isEmpty()) {
            throw new NUStudyCommandException("""
                    Invalid edit command format.
                    Usages: edit <old course name> <new course name> OR
                            edit <course code> <session index> <new study duration in hours> OR
                            edit <course code> <session index> <new date>""");
        }

        final String[] parts = arguments.split("\\s+");
        if (parts.length <= 1) {
            throw new NUStudyCommandException("""
                    Invalid edit command format.
                    Usages: edit <old course name> <new course name> OR
                            edit <course code> <session index> <new study duration in hours> OR
                            edit <course code> <session index> <new date>""");
        } else if (parts.length == 2) {
            return new EditCourseNameCommand(arguments);
        } else if (parts.length == 3) {
            return new EditSessionCommand(arguments);
        }
        throw new NUStudyCommandException("""
                Invalid edit command format.
                Usages: edit <old course name> <new course name> OR
                        edit <course code> <session index> <new study duration in hours> OR
                        edit <course code> <session index> <new date>""");
    }

    /**
     * Parses the delete command arguments for deleting courses or sessions.
     *
     * @param arguments The command arguments to parse for deleting.
     *
     * @return A {@DeleteByDateCommand} instance if a valid date is provided, a {@DeleteCourseCommand} instance if only
     *         a course name is provided, or a {@DeleteSessionCommand} instance if a course name and index are
     *         provided.
     *
     * @throws NUStudyCommandException If the command format is invalid.
     */
    private static Command parseDeleteCommand(final String arguments) throws NUStudyCommandException {
        if (arguments.isEmpty()) {
            throw new NUStudyCommandException("""
                    Invalid delete command format.
                    Usage: delete <date> OR delete <course> OR delete <course> <index>""");
        }

        final String[] parts = arguments.split("\\s+");

        if (parts.length == 1) {
            if (DateParser.isValidDate(parts[0])) {
                return new DeleteSessionByDateCommand(parts[0]);
            } else {
                return new DeleteCourseCommand(parts[0]);
            }
        } else if (parts.length == 2) {
            return new DeleteSessionByIndexCommand(parts[0], parts[1]);
        } else {
            throw new NUStudyCommandException("""
                    Invalid delete command format.
                    Usage: delete <date> OR delete <course> OR delete <course> <index>""");
        }
    }

    /**
     * Parses the filter command arguments for filtering courses by name or date.
     *
     * @param arguments The command arguments to parse for filtering.
     *
     * @return A {@link FilterByNameCommand} instance if a course name is provided, a {@link FilterByDateCommand}
     *         instance if a date is provided, or {@link FilterByNameAndDateCommand} for course+date.
     *
     * @throws NUStudyCommandException If the command format is invalid.
     */
    private static Command parseFilterCommand(final String arguments) throws NUStudyCommandException {
        if (arguments.isEmpty()) {
            throw new NUStudyCommandException(
                    "Invalid filter command. Usage: filter <course> OR filter <date> OR filter <course> <date>");
        }

        final String[] parts = arguments.split("\\s+");
        if (parts.length == 1) {
            // Accept single-token date-only filters -> let the FilterByDateCommand handle parsing/invalid-date
            if (DateParser.isValidDate(parts[0]) || looksLikeDate(parts[0])) {
                return new FilterByDateCommand(parts[0]);
            }
            // single token that's not a date -> treat as course-name filter
            return new FilterByNameCommand(arguments);
        } else if (parts.length == 2) {
            // If second token looks like a date -> course + date filter (route so that command can show invalid-date
            // messages)
            if (DateParser.isValidDate(parts[1]) || looksLikeDate(parts[1])) {
                return new FilterByNameAndDateCommand(parts[0], parts[1]);
            } else {
                throw new NUStudyCommandException(
                        "Invalid filter command. Usage: filter <course> OR filter <date> OR filter <course> <date>");
            }
        }

        throw new NUStudyCommandException("Invalid filter command. Supported forms: filter <course> | filter <date>");
    }

    /**
     * Checks if a token resembles a date format (e.g., contains digits and date separators).
     *
     * @param token The token to check.
     *
     * @return true if the token looks like a date, false otherwise.
     */
    private static boolean looksLikeDate(final String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }
        String t = token.trim();
        // basic patterns: digit groups separated by / or -
        return t.matches("^\\d{1,4}([/-])\\d{1,2}\\1\\d{1,4}$");
    }
}
