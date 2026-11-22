# Java core traps

**A collection of code samples showing less intuitive behavior for Java code**

This project attempts to gather a few common "traps", where naive intuition can fall short,
and sometimes it comes down to understanding the rules and how they apply. 
The rules come from two places: 
- Java Language Specification, which the compiler and the JVM enforce
- Contracts in the JDK's Javadoc, which mostly nobody enforces: break one and the code still runs, just wrongly.

## Scope

- **Topics:** equality and hashing, numbers (`BigDecimal`, boxing, overflow), collections,
  `finally`, overloading, generics
- **Tools:** Java, JUnit 5
- **Not covered:** concurrency, which has its own projects

## Exercises

1. **`EqualsWithoutHashCode`** shows how a `HashSet` decides two objects are the same, and why
   overriding `equals` alone isn't enough. It's the contract behind every entity or value object
   you put in a set or use as a map key.
2. **`ChangingAKeyInASet`** changes a `User`'s email, the field its `equals` and `hashCode` use,
   after the user went into a `HashSet`. It shows that a hash collection remembers where it put an
   object, not what the object looks like now, which is why keys should be immutable.
3. **`BigDecimalEquality`** compares the same amount written with different decimals, and builds
   one from a `double`. It shows that `BigDecimal` has two ideas of "equal", and which one a
   `HashSet` uses, which matters in any code that handles money.
4. **`ComparingBoxedNumbers`** compares `Long` ids the way an entity holds them. It shows why `==`
   on boxed numbers can pass in tests with small ids and fail in production with larger ones.
5. **`ReturnAndFinally`** records the order in which `try`, `return` and `finally` run, and puts a
   `return` in both `try` and `finally`. It's the guarantee that cleanup code runs even after a
   method has decided what to return, and the reason the compiler warns when `finally` returns.
6. **`ListRemoveByIndexOrValue`** calls `remove(1)` and `remove(Integer.valueOf(1))` on the same
   list of `Integer`s. It shows which of the two `remove` methods Java picks when an argument could
   mean an index or a value, and how boxing the argument changes that choice.
7. **`ListFromArraysAsList`** adds to a list made with `Arrays.asList`, and changes the array
   behind it. It shows that the result looks like any other `List` but doesn't behave like an
   `ArrayList`.
8. **`RemovingWhileIterating`** removes elements from a list inside a for-each loop, and with
   `removeIf`. It shows what the loop's iterator checks, and the safe way to filter a list in place.
9. **`OverloadingMeetsOverriding`** sends a message through a `Notifier` and an `EmailNotifier`,
   one method overloaded and one overridden. It separates what the compiler decides, from the
   declared type, from what the JVM decides at runtime, from the actual object.
10. **`IntegerOverflow`** takes the absolute value of the smallest `int`, and multiplies `int`s
    into a `long`. It shows that `int` arithmetic wraps around silently, and when the widening to
    `long` happens too late.
11. **`GenericsAtRuntime`** compares the classes of a `List<String>` and a `List<Integer>` at
    runtime, to show what type erasure leaves of a generic type once the code runs.
12. **`HeapPollution`** adds an `Integer` to a `List<String>` through a raw `List`, as old code
    might, then reads it back. It shows what erasure costs: the bad element gets in without an
    error, and the failure shows up later, somewhere else.

