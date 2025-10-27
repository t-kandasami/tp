# Developer Guide

## Table of Contents

- [Acknowledgements](#acknowledgements)
- [Design & implementation](#design--implementation)
  - [UI Component](#ui-component)
  - [Parser Component](#parser-component)
  - [Command Component](#command-component)
  - [Course Component](#course-component)
  - [Session Component](#session-component)
  - [Storage Component](#storage-component)
  - [Reset Component](#reset-component)
- [Appendix](#appendix)
  - [Product scope](#product-scope)
    - [Target user profile](#target-user-profile)
    - [Value proposition](#value-proposition)
  - [User Stories](#user-stories)
  - [Non-Functional Requirements](#non-functional-requirements)
  - [Glossary](#glossary)
- [Instructions for manual testing](#instructions-for-manual-testing)

## Acknowledgements

NUStudy uses the following tools for development and testing:

- [JUnit 5](https://junit.org/junit5/) - For unit testing
- [Gradle](https://gradle.org/) - For build automation
- [PlantUML](https://plantuml.com/) - For creating UML diagrams

## Design & implementation

### UI Component

### Parser Component

### Command Component

<u>Overview</u>

The command component is centered on the `Command` interface which defines an execution contract for all commands.
Each command implements this interface and provide its own specific execution logic. This ensures consistent
behaviour across commands.

<u>Implementation Details</u>

The following diagram is the class diagram for `Command` and its subclasses.

![Command Class Diagram](diagrams/CommandClassDiagram.png)

The `Command` interface is implemented by the following command classes:

- **Add Commands**: `AddCourseCommand`, `AddSessionCommand`
- **Delete Commands**: `DeleteCourseCommand`, `DeleteSessionByDateCommand`, `DeleteSessionByIndexCommand`
- **Edit Commands**: `EditCourseNameCommand`, `EditSessionCommand`
- **List Commands**: `ListCourseCommand`, `ListCourseHoursPerSessionCommand`
- **Other Commands**: `ResetCourseHoursCommand`, `ExitCommand`


<u>Methods</u>

The `Command` interface defines the following methods:

- `execute(CourseManager courses, SessionManager sessions)`: Executes the specific command logic. Takes the course and
  session managers as parameters to perform operations. Throws `NUStudyException` if execution fails.
- `isExit()`: Returns a boolean indicating whether the command is an exit command.

The following sections provide detailed examples of specific command implementations to illustrate how the `Command`
interface is used in practice.

### List Course Command
<u> Overview </u>

`ListCourseCommand` is a concrete implementation of the `Command` interface that lists all courses managed by
`CourseManager`. It performs basic sanity checks, logs its actions, and delegates the rendering of the course list to
`UserInterface`.

<u> Implementation Details </u>

#### Package `arpa.home.nustudy.command`

#### Class `public class ListCourseCommand implements Command`

#### Dependencies
| Component        | Responsibility                                           |
| ---------------- | -------------------------------------------------------- |
| `CourseManager`  | Provides the collection of courses to be listed.         |
| `SessionManager` | Present for consistency with command signature.         |
| `UserInterface`  | Renders the course list or an appropriate message.       |
| `NUStudyException` | Thrown if listing fails (not expected in current impl). |
| `Logger`         | Records info/fine level logs for tracing execution.      |

<u> Workflow </u>

The activity diagram below illustrates the ListCourseCommand.execute() flow:
![](diagrams/ListCourseCommandActivityDiagram.png)

Key steps:
- Assert non-null arguments (both `courses` and `sessions`) to catch programming errors early.
- Log the start of command execution at INFO level.
- If the course list is empty, prompt the user with "No courses available" and stop.
- Otherwise, invoke `UserInterface.printCourseList(courses)` to render the list.
- Log completion at FINE level and return.

Notes:
- The assertions are defensive checks intended for development/testing (enabled with -ea). Production callers should ensure valid arguments.

### Delete Course Command

<u> Overview </u>

`DeleteCourseCommand` is a concrete implementation of the `Command` interface in the NUStudy system. The primary
function is to handle user requests to delete an existing course by its name from the courses `ArrayList` managed by
the `CourseManager`.

This command does the following:

- Validate the user input
- Searches for the specified course.
- Removes it from the internal course list if found.
- Performs consistency checks post-deletion.
- Logs all major steps and errors for traceability.
- Displays a confirmation message to the user.

<u> Implementation Details </u>

#### Package `arpa.home.nustudy.command`

#### Class `public class DeleteCourseCommand implements Command`

#### Dependencies

| Component                      | Responsibility                                                            |
|--------------------------------|---------------------------------------------------------------------------|
| `CourseManager`                | Manages the list of existing courses, including lookup and deletion.      |
| `SessionManager`               | Provided for command consistency, though not used directly in this class. |
| `UserInterface`                | Displays confirmation messages to the user.                               |
| `Storage`                      | Used as a reference for the logger name.                                  |
| `NUStudyException`             | Thrown for invalid inputs or deletion failures.                           |
| `NUStudyNoSuchCourseException` | Thrown when the specified course does not exist.                          |
| `Logger`                       | Provides runtime logging for debug, info, and error tracking.             |

<u> Workflow </u>

The following activity diagram illustrates the complete removing course workflow:
![](diagrams/DeleteCourseCommandActivityDiagram.png)

### Course Component

<u>Overview</u>

The Course Component is responsible for representing and managing course-related entities in the NUStudy Application.
It consists of 2 main classes:

1. `Course` - a Class that encapsulates all information about a Course.
2. `CourseManager` - a Class that manipulates and maintains a collection of Course Objects.

These 2 classes forms the layer for handling the entity of Course. It is responsible for the CRUD of the entity Course.

<u>Implementation Details</u>

The following diagram is the class diagram for `Course` and `CourseManger`

![](diagrams/CourseClassDiagram.png)

The `CourseManager` class acts as a controller or manager, handling a dynamic list of Course instances using an internal ArrayList.

<u>Course Methods</u>

The `Course` class defines the following methods
* `Course(String courseName)`: Constructs a new Course with the given name.
* `getCourseName()`: Retrieves the name attribute of a Course.
* `setCourseName(String courseName)`: Updates the course name of a Course used for the edit feature.
* `toString()`: Returns the course name as a readable string.
* `toStorageString()`: Returns the course data formatted for file storage.

<u>CourseManager Methods</u>

The `CourseManager` class defines the following methods
* `add(Course course)`: Adds a Course Object to the list.
* `delete(Course course)`: Removes a Course Object from the list.
* `getCourses()`: Retrieves all courses managed by this class.
* `findCourse(String courseName)`: Searches for a course by name.
* `iterator()`: Returns an iterator to traverse through all courses.

### Session Component

### Storage Component

<u>Overview</u>

The `Storage` class is responsible for managing the saving and loading of Course and Study Session to and from the
local machine. NUStudy will load the saved data from the saved txt file if present when starting the application, and
saving all data back to the saved txt file when application exits.

<u>Implementation Details</u>

The Storage component consists of the following key classes:

* `Storage` - Main class that handles file I/O operations for saving and loading data
* `Course` - Represent a course with a name, provides serialisation via `toStorageString()`
* `Session` - Represents a study session linked to a course with logged hours, provides serialisation via
  `toStorageString()`
* `CourseManager` - Manages the collection of courses
* `SessionManager` - Manages the collection of study sessions

The class diagram below illustrates the relationships between these classes:

![Storage Class Diagram](diagrams/StorageClassDiagram.png)

<u>File format and conventions</u>

`Storage` creates a `NUStudy.txt` in the directory `./data` when the application first run and performs a save
operation. Subsequent runs of the application will load data from this file.

The storage file is lined-based, with each line representing a single record:

**Format**

```
C|<courseName>
S|<courseName>|<loggedHours>
```

Where:

- `C|` prefix denotes a Course record
- `S|` prefix denotes a Session record
- Fields are separated by the pipe character `|`
- `<courseName>` is the course identifier
- `<loggedHours>` is an integer representing study hours

*Examples*

```
C|CS2113
C|MA1508E
S|CS2113|2
S|CS2113|5
S|MA1508E|6
```

This example shows:

- Two courses: CS2113 and MA1508E
- Three study sessions: two for CS2113 (2 hours and 5 hours) and one for MA1508E (6 hours)

<u>Save Operation</u>

The following sequence diagram illustrates how data is stored to storage:

![Storage Save Sequence Diagram](diagrams/StorageSaveSequenceDiagram.png)

<u>Load Operation</u>

The following sequence diagram illustrates how data is loaded from storage:

![Storage Load Sequence Diagram](diagrams/StorageLoadSequenceDiagram.png)

How the `load` operation work:

1. `ensureParentDirectoryexists()` checks for the parent file. An error message is logged and returns `false` if 
   non-existent.
2. If parent directory exists, storage file existence is checked. If `exists()` on the storage file returns false, 
   empty dataset is initialised. Else, data is loaded in.
3. Data is parsed in line by line using `nextLine()`. Lines with prefix `C|` are parsed as courses with `parseCourse
(line)` while lines with prefix `S|` are parsed as sessions with `parseSession(line)`.
4. Every line is validated in the format variables (prefix, number of segments, non-null values). For sessions, the 
   referenced course must exist in the corresponding `CourseManager` instance.
5. Warnings are logged for invalid lines - such lines are skipped.
6. Only valid courses and sessions are added to the `CourseManager` and `SessionManager` instances respectively.

### Reset Component

<u> Overview </u>

The Reset Functionality enables users to clear logged study hours for either all or a specified course. A **double 
confirmation flow** is developed to prevent any unintended and thus, accidental deletions.

<u> Implementation details </u>

1. First Level Confirmation:
- Users must confirm with `y` or `n` regardless of capitalisation. This prompt loops until valid input is received
- If `n` is received, reset operation is cancelled
2. Second Level Confirmation:
- Users are to prompted to type the `safeword` - `RESET ALL` for all courses and `RESET` for a specific course
- The `safeword` must strictly be equivalent, else reset operation is cancelled

<u> Reset Workflow </u>

The following activity diagram illustrates the complete reset workflow:

![Reset Activity Diagram](diagrams/ResetFunctionDiagram.png)

<u> Design Considerations </u>

**Aspect: Confirmation mechanism**

* **Alternative 1 (current choice)**: Dual-level confirmation with `safeword`
  * Pros: By strictly requiring the user to clarify his/her final intent, this ensures maximum prevention in 
    accidental deletion
  * Cons: Slower user experience due to increased user interaction
* **Alternative 2**: Single-level confirmation
  * Pros: Quicker user experience, straightforward design
  * Cons: Greater risk of accidental data wipe, especially for `reset all` usage
* **Alternative 3**: Zero confirmation
  * Pros: Quickest operation, minimal required user interaction
  * Cons: Reckless design, leading to unacceptable risk of data wipe

{*more aspects and alternatives to be added*}

## Appendix

### Product scope

#### Target user profile

{Describe the target user profile}

#### Value proposition

{Describe the value proposition: what problem does it solve?}

### User Stories

| Version | As a ... | I want to ...             | So that I can ...                                           |
|---------|----------|---------------------------|-------------------------------------------------------------|
| v1.0    | new user | see usage instructions    | refer to them when I forget how to use the application      |
| v2.0    | user     | find a to-do item by name | locate a to-do without having to go through the entire list |

### Non-Functional Requirements

{Give non-functional requirements}

### Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
