# Foo Kang - Project Portfolio Page

## Overview

NUStudy is a desktop app for managing a student's study sessions, optimised for use via a command-line interface (CLI).

It is written in Java and uses Gradle for Build Management. JUnit for unit-testing.

### Summary of Contributions

#### 1) Feature Implemented

- **AddCourseCommand** — Add a new course to the study schedule  
  _PR:_ [#27](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/27)

  - **Description:** Introduced `AddCourseCommand` to register courses via CLI.
  - **Justification:** Foundation for organizing sessions by course, enabling downstream features (session creation, tracking).
  - **Notes:** Input validation, duplicate checks, and user feedback messages.

- **Persistent Storage (`Storage` class)** — Save/Load data between runs  
  _PR:_ [#53](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/53)

  - **Description:** This feature saves course and session data to a local storage file `NUStudy.txt` when the application session ends.
  - **Justification:** Ensures that users' study data is preserved across application sessions.
  - **Highlights:** This implementation required careful handling of file I/O operations and data serialization to ensure data integrity.
  - **Credits:** Implementation completed fully by me. My teammate, Andrew Gan, contributed to minor bug fixes.

- **Flexible Date Input (`DateParser`)** — Accept multiple date formats
  _PR:_ [#76](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/76)

  - **Description:** This feature allows users to input dates in various formats, enhancing user experience.
  - **Justification:** Improves usability by accommodating different user preferences for date input.
  - **Highlights:** This implementation involved creating a robust parsing mechanism that can handle multiple date formats and edge cases.

- **EditSessionDateCommand** — Update a session’s date  
  _PR:_ [#80](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/80)
  - **Description:** Enabled in-place session date edits with validation and user prompts.
  - **Justification:** Allows users to correct or update session dates, improving data accuracy.
  - **Highlights:** Integrated with `DateParser` for flexible date input handling.

#### 2) Enhancements to Existing Features

- **Store date for study sessions**  
  _PR:_ [#76](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/76)

  - Extended session model to include a date field integrated with `DateParser`.

#### 3) Code Contributed

- **RepoSense Report:**  
  [View breakdown](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=fookang&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-09-19T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&filteredFileName=&checkedFileTypes=docs~functional-code~test-code~other)

#### 4) Testing

- Wrote unit tests for `CommandParser`, `Storage`, `EditSessionCommand` and `DateParser` using **JUnit**.

#### 5) Documentation

- **User Guide**

  - Added “Edit study session date for a course” section with examples
    _PR:_ [#78](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/78)

- **Developer Guide**

  - Documented Storage class design, save sequence diagrams  
    _PR:_ [#65](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/65)

  - Documented command component architecture and class diagrams  
    _PR:_ [#93](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/93)

#### 6) Community Involvement

- PRs reviewed (with non-trivial review comments): [#71](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/71)
