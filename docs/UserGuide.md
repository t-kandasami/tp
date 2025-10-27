# User Guide

## Introduction

NUStudy is a desktop app for managing study hours, optimised for use via a command-line interface (CLI).

## Content Log
* [Quick Start](#quick-start)
* [Features](#features)
    * [Add a course](#add-a-course)
    * [Add a study session with hours](#add-a-study-session-with-hours)
    * [List all added courses](#list-all-added-courses)
    * [List study sessions for a course](#list-study-sessions-for-a-course)
    * [Edit study sessions for a course](#edit-study-sessions-for-a-course)
    * [Edit a courseName](#edit-a-coursename-)
    * [Filter by course name](#filter-by-course-name)
    * [Filter by date](#filter-by-date)
    * [Filter by course name and date](#filter-by-course-name-and-date)
    * [Reset hours for a course](#reset-hours-for-a-course)
    * [Delete a course](#delete-a-course)
    * [Delete a session by index](#delete-a-session-by-index-delete)
    * [Delete a session by date](#delete-a-session-by-date-delete-)
    * [Exit NUStudy](#exit-nustudy)
    * 

## Quick start

1. Ensure that you have Java Runtime Environment (JRE) 17 or above installed in your computer
1. Download the latest version of NUStudy from [here](
   https://github.com/AY2526S1-CS2113-W14-2/tp/releases/latest/download/NUStudy.jar)
1. Copy the file to the folder you want to use as the home folder for NUStudy
1. Run the JAR file in the terminal with `java -jar ./path/to/NUStudy.jar`

## Features

### Add a course

Add a course to the course book

Format: `add <course code>`

Example: `add CS2113`

Expected output:

```
Good Job! I have added CS2113
```

### Add a study session with hours

Add a course study session to the course book

Format: `add <course code> <study duration (in hours)> <Date (optional)>`

**Note:** If date is not provided, today's date will be used.

Supported date formats:

- `yyyy-MM-dd` (e.g., 2025-10-25)
- `d/M/yyyy` (e.g., 25/10/2025)
- `d-M-yyyy` (e.g., 25-10-2025)

Example: `add CS2113 5`

Expected output:

```
Good Job! You have studied 5 hours for CS2113
```

### List all added courses

Show a list of all courses in course book

Format: `list`

Expected output:

```
List of courses
1. CS2113
2. CS1231
3. MA1501
```

### List study sessions for a course

Show a list of all study sessions for a course in the system

Format: `list <course code>`

Example: `list CS2113`

Expected output:

```
List of Study Sessions
1. CS2113 - 3 hours at 25 Oct 2025
2. CS2113 - 1 hours at 25 Oct 2025
3. CS2113 - 4 hours at 25 Oct 2025
```

### Edit study sessions for a course

Edit the study session's date or study duration

Format: `edit <course code> <session index> <new study duration | new date>`

Supported date formats:

- `yyyy-MM-dd` (e.g., 2025-10-25)
- `d/M/yyyy` (e.g., 25/10/2025)
- `d-M-yyyy` (e.g., 25-10-2025)

Example: `edit CS2113 1 23/10/2025`

Expected output

```
Session Date change to 23 Oct 2025
```

### Edit a courseName 

Edit an old course name with a new course name. The old course has to be exist beforehand.  

Format: `edit <old course name> <new course name>`

Example: `edit CS2113 MA1511`

Expected output:

```
NOTE: Successfully renamed course cs2113 to ma1511
```


### Reset hours for a course

Reset session hours for a specified course or all courses in the course book.
Double confirmation flow is implemented to prevent accidental deletion.
At second confirmation, if required safeword does not match user's input, reset operation cancels.

Format: `reset <course>` or `reset all`

Example: `reset CS2113`

Example output (_`>` indicates user input_):
```
Are you sure of resetting hours for CS2113 (y/n)?
> y
Please type "RESET" to double confirm reset hours for CS2113
> RESET
Confirmation successful
Logged hours for CS2113 have been reset
```

Example: `reset all`

Example output (_`>` indicates user input_):
```
Are you sure you want to reset hours for ALL courses? (y/n)
> y
Please type "RESET ALL" to double confirm reset hours for CS2113
> RESET ALL
Confirmation successful
Logged hours for all courses have been reset
```


### Delete a course

Delete the specific course in the course book

Format: `delete <course code>`

Example: `delete CS2113`

Expected output:

```
NOTE: We have deleted CS2113 from Course Book
```

### Delete a session by index: `delete`

Delete the specified session of a specified course in the course book.

Format: `delete <course code> <index>`

Example:
- Type `list CS2113` to view listed courses for `CS2113` and choose index `2` to delete.
- Type `delete CS2113 2` to delete index `2` of `CS2113`'s sessions.

Expected output:
```
Session 2 was successsfuly deleted for CS2113
Deleted session: 2 - 5 hours on 26 Oct 2025
```
### Delete a session by date: `delete` 

Delete the sessions from a specified date for all courses in the course book.

Format: `delete <date>`

Supported date formats:

- `yyyy-MM-dd` (e.g., 2025-10-25)
- `d/M/yyyy` (e.g., 25/10/2025)
- `d-M-yyyy` (e.g., 25-10-2025)

Example:
- Type `list <course>` to view listed courses for any course and choose date to delete sessions from.
- Type `delete 26/10/2025` to delete all sessions from al courses logged on 26 Oct 2025.

Expected Output:
```
Successfully deleted 3 session(s) on 26 Oct 2025.
```
### Filter by course name

Show all courses whose codes or names match the given course keyword.
- Format: `filter <course>`
- Output: Displays the matching courses only (no session details).

Example: `filter ma1511`

Expected output:
```
Filtered courses matching "ma1511"
1. MA1511
2. MA1511X
```

Behaviour notes
- This filter is read-only and does not modify stored data.
- Matching for course keywords is case-insensitive and uses substring matching.
- The filtered list retains the original indices from the full course list so the printed numbers correspond to each 
  course's index in the complete list (useful when editing/deleting by index).
- If no matches are found, a clear empty-filter message is shown (e.g., `No courses matched "XYZ"`).

### Filter by date

Show all courses that have sessions on the specified date (useful to see which courses had activity that day).
- Format: `filter <date>`
- Supported date formats:
  - `yyyy-MM-dd` (e.g., 2025-10-25)
  - `d/M/yyyy` (e.g., 25/10/2025)
  - `d-M-yyyy` (e.g., 25-10-2025)
- Output: Displays the list of courses that have one or more sessions on that date.

Example: `filter 23/10/2025`

Expected output:
```
Courses with sessions on 23 Oct 2025
1. CS2113
2. MA1511
3. CS1231
```

Behaviour notes
- This filter is read-only and does not modify stored data.
- The filtered list retains the original indices from the full course list so the printed numbers correspond to each
  course's index in the complete list (useful when editing/deleting by index).
- If no matches are found, a clear empty-filter message is shown (e.g., `No sessions found on 23 Oct 2025`).

### Filter by course name and date

Show sessions for the specified course that occurred on the given date.
- Format: `filter <course> <date>`
- Supported date formats (same as above).
- Output: Displays the sessions for that course on that date (session list/indices and hours).

Example: `filter ma1511 23/10/2025`

Expected output:
```
Sessions for MA1511 on 23 Oct 2025
1. MA1511 - 3 hours at 23 Oct 2025
2. MA1511 - 1 hours at 23 Oct 2025
```

Behaviour notes
- This filter is read-only and does not modify stored data.
- Matching for course keywords is case-insensitive and uses substring matching.
- Session indices printed are relative to the course's session list (useful when editing/deleting by index).
- If no matches are found, a clear empty-filter message is shown (e.g., `No sessions found for MA1511 on 23 Oct 2025`).

### Exit NUStudy

Exit the NUStudy program

Format: `exit`

Expected output:

```
Exiting App. Goodbye!
```

## Command summary

* Add a course: `add cs2113`
* Add a study session with hours: `add cs2113 5`
* List all added courses: `list`
* List study sessions for a course: `list CS2113`
* Delete a course: `delete CS2113`
* Filter by course name: `filter ma1511`
* Filter by date: `filter 23/10/2025`
* Filter by course name and date: `filter ma1511 23/10/2025`
* Exit NUStudy: `exit`
