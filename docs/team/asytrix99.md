# Andrew Gan's Project Portfolio Page

## Project: NUStudy
NUStudy, an NUS CS2113 team project (tP), by team W14-2, AY2526S1, is a desktop app for managing study hours, optimised 
for use via a command-line interface (CLI). It is written in Java.

Given below are my contributions to the project.

- __New Feature__: Implemented the `resetCourseHoursCommand` class:
[#36](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/28) ,[#28](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/28).
  - What it does: Allows users to reset the logged study hours for a specified course or all courses.
  - Justficiation: This feature improves usability by giving users flexibility to start new every semester or clear 
    hours for a specific course without deleting the course entirely.
  - Highlighs: This implementation required integrating the feature across multiple components while maintaining 
    the course-session connection. It also involves handling exceptions with a double confirmation flow to prevent 
    accidental data wipe. 
  - Credits: Design inspired from this
      [article](https://product-alpaca.medium.com/double-confirmation-to-be-or-not-to-be-ebae44c0609a). Implementation
      completed fully by me.
- __New Feature__: Implemented the data loading component under the `Storage` class:
[#63](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/63).
  - What it does: Loads and parses stored course and sessiond data automatically from a local storage file `NUStudy.
  txt` when a new application session starts. This allows users to resume study progress tracking seamlessly across 
    multiple application sessions.
  - Justification: Without automatic data loading, all previously recorded study hours will be wiped when the 
    application session closes. This feature improves user experience and persistence.
  - Highlights: This implementation requires careful handling of file I/O operations and parsing logic. It uses a 
    buffer of class `BufferedReader` to safely transfer the data over. To handle exceptions, corrupted entires are 
    skipped to prevent application session crashes at runtime.
  - Credits: Idea of `BufferedReader` is obtained from this [article](https://www.geeksforgeeks.org/java/java-io-bufferedreader-class-java/).
  Implementation is completed fully by me. My teammate, Foo Kang, contributed to minor bug fixes. 
- __Code Contributed__:
  [RepoSense link](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=asytrix99&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-09-19T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=asytrix99&tabRepo=AY2526S1-CS2113-W14-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)
- __Enhancement to existing features__:
  - Implemented `DeleteSessionByIndexCommand` and `DeleteSessionByDateCommand` classes for enhanceed flexibility for 
    deleting sessions by date for all couress or a specified index for a specified course:
  [#85](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/85).
  - Added JUnit 5 test cases for my implemented features and enhancements. Raised total method 
    coverage to 100%: [#90](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/90).
- __Documentation__:
  - User Guide:
    - Added documentation for features `delete course by index`, `delete course by date` and `reset course 
      hours`: [#90](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/90).
    - Added documentation for data integrity and safety for clear understanding of how data loading is handled: 
      [#180](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/180).
  - Developer Guide:
    - Added implementation details, activity flow diagram and design considerations of the `reset` feature:
    [#71](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/71).
    - Added implementation details and sequence diagram for the data loading component feature:
    [#71](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/71).
      - Added list of validation checks and execution upon detection of invalid entries:
      - [#180](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/180).
- __Community__:
  - PRs reviewed (with non-trivial review comments): [#65](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/65).
  - The confirmation handler feature I implemented was adopted by my teammate, Kandasami:
  [#86](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/86/commits/7851392286677e1166ed8fa23afafc82f468bb60).