# User Guide

## Introduction

NUStudy is a desktop app for managing study hours, optimised for use via a command-line interface (CLI).

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

### Delete a course

Delete the specific course in the course book

Format: `delete <course code>`

Example: `delete CS2113`

Expected output:

```
NOTE: We have deleted CS2113 from Course Book
```

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
* Exit NUStudy: `exit`
