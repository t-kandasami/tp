# User Guide

## Introduction

NUStudy is a **desktop app for managing study hours, optimised for use via a command-line interface** (CLI). If you can
type fast, NUStudy can get your study management tasks done faster than traditional GUI apps.

## Table of contents

- [**Quick start**](#quick-start)
- [**Features**](#features)
    - [Add a course](#add-a-course)
    - [Add a study session with hours](#add-a-study-session-with-hours)
    - [List all added courses](#list-all-added-courses)
    - [List study sessions for a course](#list-study-sessions-for-a-course)
    - [Edit a course name](#edit-a-course-name)
    - [Edit study session hours for a course](#edit-study-session-hours-for-a-course)
    - [Edit study session date for a course](#edit-study-session-date-for-a-course)
    - [Filter by course name](#filter-by-course-name)
    - [Filter by date](#filter-by-date)
    - [Filter by course name and date](#filter-by-course-name-and-date)
    - [Reset hours for a course](#reset-hours-for-a-course)
    - [Delete a course](#delete-a-course)
    - [Delete a session by index](#delete-a-session-by-index)
    - [Delete a session by date](#delete-a-session-by-date)
    - [Exit NUStudy](#exit-nustudy)
- [**Command summary**](#command-summary)
- [**Appendix: Supported date formats**](#appendix-supported-date-formats)

## Quick start

1. Ensure that you have Java Runtime Environment (JRE) **17 or above** installed on your computer. If you do not have
   JRE 17 installed, you can [download Temurin JRE 17](https://adoptium.net/temurin/releases?version=17&os=any&arch=any
   ).
1. Download the latest version of NUStudy from [GitHub releases](
   https://github.com/AY2526S1-CS2113-W14-2/tp/releases/latest/download/NUStudy.jar)
1. Copy the downloaded JAR file to the folder you want to use as the _home folder_ for NUStudy
1. Run the application in the terminal with `java -jar ./path/to/NUStudy.jar`

## Features

### Add a course

Add a course to the course book.

Format: `add <course code>`

Example: `add CS2113`

Expected output:

```
Good Job! I have added CS2113
```

### Add a study session with hours

Add a course study session to the course book.

Format: `add <course code> <study duration in hours> [date]`

> **Note:**
>
> If `date` is not provided, today's date will be used.

See [Appendix: Supported date formats](#appendix-supported-date-formats) for valid date formats.

Example: `add CS2113 5`

Expected output:

```
Good Job! You have studied 5 hours for CS2113
```

### List all added courses

Show a list of all courses in the course book.

Format: `list`

Expected output:

```
List of courses:
1. CS2113
2. CS1231
3. MA1501
```

### List study sessions for a course

Show a list of all study sessions for a course in the course book.

Format: `list <course code>`

Example: `list CS2113`

Expected output:

```
List of study sessions
1. CS2113 - 3 hours at 25 Oct 2025
2. CS2113 - 1 hours at 25 Oct 2025
3. CS2113 - 4 hours at 25 Oct 2025
```

### Edit a course name

Edit an old course name with a new course name. The old course has to exist beforehand.

Format: `edit <old course name> <new course name>`

Example: `edit CS2113 MA1511`

Expected output:

```
NOTE: Successfully renamed course CS2113 to MA1511
```

### Edit study session hours for a course

Edit the study session hours for a course in the course book.

Format: `edit <course code> <session index> <new study duration in hours>`

Example: `edit CS2113 1 2`

Expected output:

```
Session hours changed to 2 hours
```

### Edit study session date for a course

Edit the study session date for a course in the course book.

Format: `edit <course code> <session index> <new date>`

See [Appendix: Supported date formats](#appendix-supported-date-formats) for valid date formats.

Example: `edit CS2113 1 23/10/2025`

Expected output:

```
Session Date change to 23 Oct 2025
```

### Filter by course name

Show all courses whose codes or names match the given course keyword.

Format: `filter <course>`

Example: `filter MA1511`

Expected output:

```
Filtered courses matching "MA1511"
1. MA1511
2. MA1511X
```

Behaviour notes:

- This filter is read-only and does not modify stored data.
- Matching for course keywords is case-insensitive and uses substring matching.
- The filtered list retains the original indices from the full course list so the printed numbers correspond to each
  course's index in the complete list (useful when editing/deleting by index).
- If no matches are found, a clear empty-filter message is shown (e.g. `No courses matched "XYZ"`).

### Filter by date

Show all courses that have sessions on the specified date (useful to see which courses had activity that day).

Format: `filter <date>`

See [Appendix: Supported date formats](#appendix-supported-date-formats) for valid date formats.

Example: `filter 23/10/2025`

Expected output:

```
Courses with sessions on 23 Oct 2025
1. CS2113
2. MA1511
3. CS1231
```

Behaviour notes:

- This filter is read-only and does not modify stored data.
- The filtered list retains the original indices from the full course list so the printed numbers correspond to each
  course's index in the complete list (useful when editing/deleting by index).
- If no matches are found, a clear empty-filter message is shown (e.g. `No sessions found on 23 Oct 2025`).

### Filter by course name and date

Show sessions for the specified course that occurred on the given date.

Format: `filter <course> <date>`

See [Appendix: Supported date formats](#appendix-supported-date-formats) for valid date formats.

Example: `filter MA1511 23/10/2025`

Expected output:

```
Sessions for MA1511 on 23 Oct 2025
1. MA1511 - 3 hours at 23 Oct 2025
2. MA1511 - 1 hours at 23 Oct 2025
```

Behaviour notes:

- This filter is read-only and does not modify stored data.
- Matching for course keywords is case-insensitive and uses substring matching.
- Session indices printed are relative to the course's session list (useful when editing/deleting by index).
- If no matches are found, a clear empty-filter message is shown (e.g. `No sessions found for MA1511 on 23 Oct 2025`).

### Reset hours for a course

Reset session hours for a specified course or all courses in the course book. Double confirmation flow is implemented to
prevent accidental deletion. At second confirmation, if the required safeword does not match user's input, the reset
operation cancels.

Format: `reset <course>`, or `reset all`

Example: `reset CS2113`

Expected output (_`>` indicates user input_):

```
Are you sure of resetting hours for CS2113 (y/n)?
> y
Please type "RESET" to double confirm reset hours for CS2113
> RESET
Confirmation successful
Logged hours for CS2113 have been reset
```

Example: `reset all`

Expected output (_`>` indicates user input_):

```
Are you sure you want to reset hours for ALL courses? (y/n)
> y
Please type "RESET ALL" to double confirm reset hours for CS2113
> RESET ALL
Confirmation successful
Logged hours for all courses have been reset
```

### Delete a course

Delete the specific course in the course book.

Format: `delete <course code>`

Example: `delete CS2113`

Expected output (_`>` indicates user input_):

```
Are you sure you want to delete CS2113 (y/n)?
> y
NOTE: We have deleted CS2113 from Course Book
```

### Delete a session by index

Delete the specified session of a specified course in the course book.

Format: `delete <course code> <index>`

Example: `delete CS2113 2`

Expected output:

```
Session 2 was successsfuly deleted for CS2113
Deleted session: CS2113 - 5 hours on 26 Oct 2025
```

### Delete a session by date

Delete the sessions from a specified date for all courses in the course book.

Format: `delete <date>`

See [Appendix: Supported date formats](#appendix-supported-date-formats) for valid date formats.

Example: `delete 26/10/2025`

Expected output:

```
Successfully deleted 3 session(s) on 26 Oct 2025.
```

### Exit NUStudy

Exit the NUStudy program

Format: `exit`

Expected output:

```
Exiting App. Goodbye!
```

## Command summary

| Type   | Action                                                                          | Format                                                             | Example                    |
|--------|---------------------------------------------------------------------------------|--------------------------------------------------------------------|----------------------------|
| Add    | [Add a course](#add-a-course)                                                   | `add <course code>`                                                | `add CS2113`               |
|        | [Add a study session with hours](#add-a-study-session-with-hours)               | `add <course code> <study duration in hours> [date]`               | `add CS2113 5`             |
| List   | [List all added courses](#list-all-added-courses)                               | `list`                                                             | `list`                     |
|        | [List study sessions for a course](#list-study-sessions-for-a-course)           | `list <course code>`                                               | `list CS2113`              |
| Edit   | [Edit a course name](#edit-a-course-name)                                       | `edit <old course name> <new course name>`                         | `edit CS2113 MA1511`       |
|        | [Edit study session hours for a course](#edit-study-session-hours-for-a-course) | `edit <course code> <session index> <new study duration in hours>` | `edit CS2113 1 2`          |
|        | [Edit study session date for a course](#edit-study-session-date-for-a-course)   | `edit <course code> <session index> <new date>`                    | `edit CS2113 1 23/10/2025` |
| Filter | [Filter by course name](#filter-by-course-name)                                 | `filter <course>`                                                  | `filter MA1511`            |
|        | [Filter by date](#filter-by-date)                                               | `filter <date>`                                                    | `filter 23/10/2025`        |
|        | [Filter by course name and date](#filter-by-course-name-and-date)               | `filter <course> <date>`                                           | `filter MA1511 23/10/2025` |
| Reset  | [Reset hours for a course](#reset-hours-for-a-course)                           | `reset <course>`, or `reset all`                                   | `reset CS2113`             |
| Delete | [Delete a course](#delete-a-course)                                             | `delete <course code>`                                             | `delete CS2113`            |
|        | [Delete a session by index](#delete-a-session-by-index)                         | `delete <course code> <index>`                                     | `delete CS2113 2`          |
|        | [Delete a session by date](#delete-a-session-by-date)                           | `delete <date>`                                                    | `delete 26/10/2025`        |
| Exit   | [Exit NUStudy](#exit-nustudy)                                                   | `exit`                                                             | `exit`                     |

## Appendix: Supported date formats

NUStudy supports the following date formats:

- `yyyy-MM-dd` (e.g. 2025-10-25)
- `d/M/yyyy` (e.g. 25/10/2025)
- `d-M-yyyy` (e.g. 25-10-2025)
