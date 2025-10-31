# Wrooi - Project Portfolio Page

## Overview

NUStudy is a desktop app for managing a student's study sessions, optimised for use via a command-line interface (CLI).
It is written in Java and uses Gradle for Build Management. JUnit for unit-testing.

The notes below summarise Ooi Wen Ree A0308622J's contributions to the project.

## Summary of Contributions

### Code contributions

- Implemented ListCourseCommand.
    - Ensured output formatting and integration with UserInterface.printCourseList.
- Implemented all filter commands:
    - FilterByNameCommand — case-insensitive name/substring course filter that preserves original course indices.
    - FilterByDateCommand — finds courses with sessions on a given date and prints original course indices.
    - FilterByNameAndDateCommand — exact course name + date filter that prints per-course session indices.
- Implemented ExitCommand (clean exit flow and user-facing exit message).
- Wrote unit tests covering the above commands (constructor/execute/isExit behaviours), increasing method/test coverage
  for these components.
- [RepoSense Link](http://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=wrooi&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-09-19T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&filteredFileName=&checkedFileTypes=docs~functional-code~test-code~other)

### User Guide (UG) contributions

- Authored the User Guide sections for:
    - Filter by course name
    - Filter by date
    - Filter by course name and date
    - List commands (clarified behaviour, examples and index retention notes)
- Updated the Command summary and examples to show the supported filter forms and index semantics.

### Developer Guide (DG) contributions

- Wrote the Appendix: Requirements section and all content through to "Appendix: Instructions for manual testing".
- Completed Architecture — UI component and Parser component sections (overview, responsibilities and implementation
  notes).
- Documented ListCourseCommand implementation and created the ListCourseCommand activity diagram; added explanation of
  assertion/logging behaviour.

### Tests & QA

- Added JUnit tests for ListCourseCommand, all filter commands and ExitCommand covering happy and edge cases for public
  methods.
- Improved test coverage for these commands and their interactions with CourseManager/SessionManager and UserInterface
  output.

### Contributions to team-based tasks

Here is a non-exhaustive list of team-tasks I contributed to (fill details as needed):

- Necessary general code enhancements
- Maintaining the issue tracker
- Release management
- Updating user/developer docs that are not specific to a feature (e.g., documenting the target user profile)
- Incorporating tools/libraries/frameworks into the product/workflow (e.g., CI, plugins)

### Review / mentoring contributions

Links and notes for PRs reviewed, mentoring and helping teammates (add links and short notes):

- PR reviews: (paste PR links & short comments)
- Helped teammates with debugging / design discussions: (brief notes)

### Enhancements implemented

Summary of small enhancements and improvements I implemented (add items and links):

- (Enhancement 1 — short description and PR/link)
- (Enhancement 2 — short description and PR/link)

### Contributions beyond the project team

Evidence of helping others or technical leadership outside the immediate team:

- Forum/help posts / answers (links)
- Bugs reported in other projects (links)
- Shared resources or technical guidance (links)

### Other

- Performed code reviews and small fixes related to the above features.
