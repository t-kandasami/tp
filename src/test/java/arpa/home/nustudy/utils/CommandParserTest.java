package arpa.home.nustudy.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import arpa.home.nustudy.command.Command;
import arpa.home.nustudy.command.EditCourseNameCommand;
import arpa.home.nustudy.command.EditSessionCommand;
import arpa.home.nustudy.command.FilterByNameCommand;
import arpa.home.nustudy.command.ListCourseCommand;
import arpa.home.nustudy.command.ListCourseHoursPerSessionCommand;
import arpa.home.nustudy.command.ExitCommand;
import arpa.home.nustudy.exceptions.NUStudyCommandException;

class CommandParserTest {
    @Test
    void parseCommand_editCommandWithTwoArgument_returnsEditCourseNameCommand()
            throws NUStudyCommandException {
        final Command command = CommandParser.parseCommand("edit CS2113 CS2103");
        assertTrue(command instanceof EditCourseNameCommand,
                "Should return EditCourseNameCommand for valid edit command");
    }

    @Test
    void parseCommand_editCommandWithThreeArguments_returnsEditSessionCommand()
            throws NUStudyCommandException {
        Command command = CommandParser.parseCommand("edit CS2113 1 22/10/2025");
        assertTrue(command instanceof EditSessionCommand,
                "Should return EditSessionCommand for valid edit command");
    }

    @Test
    void parseCommand_editCommandWithEmptyArgument_throwsException()
            throws NUStudyCommandException {
        assertThrows(NUStudyCommandException.class, () -> CommandParser.parseCommand("edit"),
                "Should throw exception when empty argument");
    }

    @Test
    void parseCommand_editCommandWithOneArgument_throwsException()
            throws NUStudyCommandException {
        assertThrows(NUStudyCommandException.class, () -> CommandParser.parseCommand("edit cs2113"),
                "Should throw exception when only one argument");
    }

    @Test
    void parseCommand_editCommandWithFourArgument_throwsException()
            throws NUStudyCommandException {
        assertThrows(NUStudyCommandException.class, () -> CommandParser.parseCommand("edit cs2113 1 22/10/2025 1"),
                "Should throw exception more than three arguments");
    }

    @Test
    void parseCommand_filterByName_returnsFilterByNameCommand() throws NUStudyCommandException {
        Command command = CommandParser.parseCommand("filter CS");
        assertTrue(command instanceof FilterByNameCommand, "Should return FilterByNameCommand for 'filter <keyword>'");
    }

    @Test
    void parseCommand_filterNoArguments_throwsException() {
        assertThrows(NUStudyCommandException.class, () -> CommandParser.parseCommand("filter"),
                "Should throw exception when filter has no arguments");
    }

    @Test
    void parseCommand_filterWithDate_throwsException() {
        // single token that is a valid date should not be parsed as name-filter in current implementation
        assertThrows(NUStudyCommandException.class, () -> CommandParser.parseCommand("filter 23/10/2025"),
                "Should throw exception for date-only filter (not yet supported by parseFilterCommand)");
    }

    @Test
    void parseCommand_listNoArgs_returnsListCourseCommand() throws NUStudyCommandException {
        Command command = CommandParser.parseCommand("list");
        assertTrue(command instanceof ListCourseCommand, "Should return ListCourseCommand for 'list'");
    }

    @Test
    void parseCommand_listWithCourse_returnsListCourseHoursPerSessionCommand() throws NUStudyCommandException {
        Command command = CommandParser.parseCommand("list CS2113");
        assertTrue(command instanceof ListCourseHoursPerSessionCommand,
                "Should return ListCourseHoursPerSessionCommand for 'list <course>'");
    }

    @Test
    void parseCommand_exitNoArgs_returnsExitCommand() throws NUStudyCommandException {
        Command command = CommandParser.parseCommand("exit");
        assertTrue(command instanceof ExitCommand, "Should return ExitCommand for 'exit' with no args");
    }

    @Test
    void parseCommand_exitWithArguments_throwsException() {
        assertThrows(NUStudyCommandException.class, () -> CommandParser.parseCommand("exit now"),
                "Should throw exception when exit has extra arguments");
    }
}
