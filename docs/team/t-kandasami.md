# T. Kandasami - Project Portfolio Page

## Overview

NUStudy is a desktop app for managing a student's study sessions, optimised for use via a command-line interface (CLI).
It is written in Java and uses Gradle for Build Management. JUnit for unit-testing.

### Summary of Contributions

#### Code Contributed

[RepoSense Link](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=t-kandasami&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-09-19T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

#### Enhancements Implemented

* **DeleteCourseCommand**: Implemented the finding of a pre-existing course and deleting it from the course list.
  [#30](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/30)
    * What it does: It allows a user to delete a course they created in the Course List.
    * Justification: This feature allows a user to keep their Course List clean and tidy. It performs a delete
      operation to the Course Management.
    * Highlights: This feature required integrating multi-level components like UI, Parser. Functions such as
      findCourse was also implemented.
* **AddStudySessionCommand with Hour**: Allow user to log their study sessions by entering the course name and the
  hours spend per session. [#37](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/37)
    * What it does: It helps a user to log amount of time spend on a study session for a particular course
    * Justification: This is the whole point of the application, Users want to keep track of their study sessions
    * Highlights: This feature gave me the idea to create another manager. It has to be simple so that the user can
      type it fast and get immediate feedback on "Good Job! You have studied _ hours for _"
* **Enhancement 1** : Delete Course with User Level Confirmation.
  [#82](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/37)
    * As delete is an important command invoked by a user, we want to confirm whether they want to actually do it, I
      used (@asytrix99) Andew's 2 Level Confirmation code but notice, I want it to be simple and short, so I used
      part of his implementation. This way I reused functions built within the team to prevent duplicates and
      unnecessary codes.
* **Enhancement 2** : Edit Course Name [#86](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/86)
    * Allowing users to edit the name of courses. This feature was built with the intention that it will update the
      sessions as well.
* **Enhancement 3** : Help Command [#175](https://github.com/AY2526S1-CS2113-W14-2/tp/pull/175)
    * Listing all the commands available in NUStudy in a clear concise list

#### Contributions to User Guide

* Edit a course name section
* Delete a Course
* Help Command

#### Contributions to Developer Guide

* **DeleteCourseCommand** : The entire section of the Command
    * The Descriptions and Activity Diagram
* **Course Component**: The entire section of Course Component
    * The Descriptions and Activity Diagram
* **Exception Component**: The entire section of the Exception Component
    * The Description and table