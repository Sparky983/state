[observer-pattern]: https://en.wikipedia.org/wiki/Observer_pattern

# Introduction

The State API is an minimal API that provides facilities to keep track of
state. State is simply a value that changes over time. Two interfaces are
provided for state:
- `State` which represents a read-only view of some state
- `MutableState` which can be mutated by the programmer

## Creating State

To create some state, simply call the `MutableState.of(initialValue)` method
which returns a `MutableState` object with the supplied initial value.
```java
MutableState<Integer> count = MutableState.of(1);

assert count.get() == 1;
```

To mutate the value, call `set(value)` on any `MutableState` object.
```java
count.set(2);

assert count.get() == 2;
```

## Read-only State

When write access to some state needs to be disallowed simply assign it to the
type `State`.
```java {3}
State<Integer> readOnlyCount = count;

readOnlyCount.set(2); // compilation error: no such method `set`
```

## Operations

### Map

The simplest operation that can be performed on a `State` object is a `map`.
This operation takes a state and applies the supplied function to every new
value.
```java
State<Integer> count = MutableState.of(2);
State<Integer> doubleCount = count.map(value -> value * 2);

assert doubleCount.get() == 4;

count.set(5);

assert doubleCount.get() == 10;
```

### Subscribe

`State` objects implement the [observer pattern][observer-pattern]. This means
that mutations to a state object can be tracked. When subscribed to, the
subscriber is notified of the current value and any subsequent values.
```java
State<Integer> count = MutableState.of(1);

count.subscribe(value -> System.out.println(value));

count.set(2);

// output:
// 1
// 2
```

Sometimes it's a requirement to subscribe to some state and then at a later
time, unsubscribe. This can be achieved by using the `Subscription` interface.
```java
State<String> words = MutableState.of("foo");

Subscription subscription = words.subscribe(word -> System.out.println(word));

words.set("bar");

subscription.cancel();

words.set("baz"); // the subscriber is only notified of the "bar" update

// output:
// foo
// bar
```
