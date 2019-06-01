# Validation

A simple and easy-to-use validation utility library.

By Tom Ansill

## Motivation

I got tired of manually including null checks, empty array checks, and various kinds of checks against bad input parameters. 
So I decided to build a library to make things a bit easier for me.

### Why not use `Objects.requireNonNull(T)`? 

`Objects.requireNonNull(T)` will throw `NullPointerException` and I disagree with this choice of exception because `NullPointerException` is supposed to be thrown in event of null object being de-referenced. Like: 
```
StringBuilder sb = null;
sb.append("something"); // <-- This will throw NullPointerException because you are trying to de-reference a null object
``` 

So, hence the name `NullPointerException`, you get the exception because you are attempting to de-reference a null value and it blows up on you. 
I don't believe this is the case here where I want to ensure that parameters that are passed in is not null. I'm not necessarily trying to dereference it.
I think `IllegalArgumentException` is more appropriate exception to best describe the problem.

## Prerequisites

* Java 8 or better
* Maven

## Download

**No Maven Repository available yet ):**

For now, you need to build and install it on your machine.

```bash
$ git clone https://github.com/tomansill/java-validation
$ cd java-validation
$ mvn install
```

Then include the dependency in your project's `pom.xml`:

```xml
<dependency>
    <groupId>com.ansill.validation</groupId>
    <artifactId>validation</artifactId>
    <version>0.1.0</version>
</dependency>
```

## How to use

### Null Checks

Use `Validation.assertNonnull(Object)` to assert that object is not null:

```java
import com.ansill.validation.Validation;

public class Application{
    public static void main(String[] args){
        String message = null;
        Application application = new Application();
        application.print(message);
    }
    public void print(String message){
        Validation.assertNonnull(message);
        System.out.println(message);
    }
}
```

When you run the code, it will yield this message:
```
Exception in thread "main" java.lang.IllegalArgumentException: Value is expected to be non-null but is found to be null
	at Application.print(Application.java:10)
	at Application.main(Application.java:7)
```

### Natural Number Checks

Use `Validation.assertNaturalNumber(long)` to assert that number is a natural number (no negative number or zero):

```java
import com.ansill.validation.Validation;

public class Application{
    public static void main(String[] args){
        Application application = new Application();
        application.setPort(0);
    }
    private int port = 80;
    public void setPort(int port){
        Validation.assertNaturalNumber(port, "port");
        this.port = port;
    }
}
```

When you run the code, it will yield this message:
```
Exception in thread "main" java.lang.IllegalArgumentException: Value in variable 'port' is expected to be a natural number (1, 2, ..., N-1, N) but it is actually not a natural number
	at Application.setPort(Application.java:10)
	at Application.main(Application.java:6)
```

### Non-Negative Checks

Use `Validation.assertNonnegative(long)` to assert that number is a positive number:

```java
import com.ansill.validation.Validation;

public class Application{
    public static void main(String[] args){
        Application application = new Application();
        application.add((short) 0, -1);
    }
    public void add(short one, long two){
        Validation.assertNonnegative(one);
        Validation.assertNonnegative(two);
        System.out.println(one + two);
    }
}
```

When you run the code, it will yield this message:
```
Exception in thread "main" java.lang.IllegalArgumentException: Value is expected to be non-negative but value is actually a negative number
	at Application.add(Application.java:10)
	at Application.main(Application.java:6)
```

### Non-Empty String Checks

Use `Validation.assertNonemptyString(String)` to assert that String is non-empty:

```java
import com.ansill.validation.Validation;

public class Application{
    public static void main(String[] args){
        Application application = new Application("");
    }
    public final String name;
    public Application(String name){
        Validation.assertNonemptyString(name, "name");
        this.name = name;
    }
}
```

When you run the code, it will yield this message:
```
Exception in thread "main" java.lang.IllegalArgumentException: Value in variable 'name' is expected to be non-empty but value is actually a empty string
	at Application.<init>(Application.java:9)
	at Application.main(Application.java:5)
```

### Non-Empty Array/Collection

Use `Validation.assertNonempty(Object[])` or `Validation.assertNonempty(Collection)` to assert that Array/Collection is non-empty:

```java
import com.ansill.validation.Validation;

import java.util.*;

public class Application{
    public static void main(String[] args){
        Application application = new Application(Collections.emptyList());
    }
    public final Collection hostnames;
    public Application(Collection hostnames){
        Validation.assertNonempty(hostnames);
        this.hostnames = hostnames;
    }
}
```

When you run the code, it will yield this message:
```
Exception in thread "main" java.lang.IllegalArgumentException: Value is expected to be non-empty but value is actually empty
	at Application.<init>(Application.java:11)
	at Application.main(Application.java:7)
```

### Array/Collection Member Null Check

Use `Validation.assertNonnullElements(Object[])` or `Validation.assertNonnullElements(Collection)` to assert that Array/Collection does not have null elements:

```java
import com.ansill.validation.Validation;

import java.util.*;

public class Application{
    public static void main(String[] args){
        Application application = new Application(Arrays.asList("google.com", null, "github.com", null, null, "reddit.com"));
    }
    public final Collection hostnames;
    public Application(Collection hostnames){
        Validation.assertNonnullElements(hostnames, false);
        this.hostnames = hostnames;
    }
}
```

When you run the code, it will yield this message:
```
Exception in thread "main" java.lang.IllegalArgumentException: Value is expected to have all of its list members to be non-null but the list contains null members. Invalid members are located at indices [1, 3, 4]
	at Application.<init>(Application.java:11)
	at Application.main(Application.java:7)
```