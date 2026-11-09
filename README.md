# Heaped & Stacked samples

Small, runnable Java projects, companions to [Heaped & Stacked](https://heapedstacked.dev).

Some ideas don't stick from reading two lines and memorizing them. Here they get learned by
running code, changing it, breaking it and checking how it really behaves.

This repository offers small, bite-sized projects to showcase a particular problem or technology
piece, understanding how it works and common quirks one might encounter when using it.

Some projects are exercises: a problem to solve, with failing tests until you do. Others are
reference samples for a technology, such as Redis, Kafka or a database engine: the basic use case
and its common pitfalls, working and ready to look up.

## How it's built

There's no build at the root. Each folder is a standalone Maven project, not a module of a parent
build: it has its own `pom.xml`, its own dependencies and its own README, and nothing is shared
between folders. A project can be copied out on its own and still build.

- **Java 21, unless the project says otherwise.** Each `pom.xml` sets its own Java version, so a
  project can target an older or newer one when its topic needs it. Its README then says which.
- **Maven, through its wrapper.** Each project has `mvnw` and `mvnw.cmd`, which download the right
  Maven version on the first run. No Maven install needed.
- **Only what the topic needs.** A project brings its own libraries and frameworks, such as Spring
  Boot, in its own `pom.xml`. Many need nothing but the JDK and JUnit 5.
- **Docker for infrastructure.** Projects that use a database, a cache or a message broker start
  the real thing in a container with Testcontainers, so Docker must be running for their tests.

## Working with a project

Clone the repository and open the folder you want in your IDE.

    git clone https://github.com/heaped-and-stacked/heaped-and-stacked-samples.git

Start with the project's README: it says what the project shows and what to run.

In an exercise, the tests on `main` fail until the work is done, and `TODO`s mark where your code
goes. The `solutions` branch has it finished, with every test passing.

## License

[MIT](LICENSE): use, copy and change the code freely; keep the license notice.
