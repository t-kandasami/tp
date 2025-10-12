package seedu.nustudy.utils;

import seedu.nustudy.command.AddCourseCommand;
import seedu.nustudy.command.Command;
import seedu.nustudy.command.DeleteCourseCommand;
import seedu.nustudy.command.ExitCommand;
import seedu.nustudy.command.ListCourseCommand;
import seedu.nustudy.command.ResetCourseHoursCommand;
import seedu.nustudy.exceptions.NUStudyCommandException;

public class Parser {
    private static ResetCourseHoursCommand resetCourseHoursCommand;

    /**
     * Returns a Command parsed from user's input
     *
     * @param input The user-inputted command string
     * @return A Command object that can execute the user's request
     * @throws NUStudyCommandException If the command is invalid
     */
    public static Command parseCommand(String input) throws NUStudyCommandException {
        String[] words = input.split("\\s+", 2);
        String command = words[0].toLowerCase();
        String arguments = words.length > 1 ? words[1] : "";

        switch (command) {
        case "add":
            return new AddCourseCommand(arguments);
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
}
