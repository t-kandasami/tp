package seedu.nustudy.utils;

import seedu.nustudy.command.Command;
import seedu.nustudy.command.AddCourseCommand;
import seedu.nustudy.command.ListCourseCommand;
import seedu.nustudy.exceptions.NUStudyCommandException;

public class Parser {
    /**
     * Return a Command parsed from user's input
     *
     * @param input The user-inputted command string
     * @return A Command object that can execute the user's request
     * @throws NUStudyCommandException If the command is invalid
     */
    public static Command parseCommand(String input)
            throws NUStudyCommandException {
        String[] words = input.split("\\s+", 2);
        String command = words[0].toLowerCase();
        String arguments = words.length > 1 ? words[1] : "";

        return switch (command) {
        case "add" -> new AddCourseCommand(arguments);
        case "list" -> new ListCourseCommand();
        default -> throw new NUStudyCommandException("Wrong command");
        };
    }
}
