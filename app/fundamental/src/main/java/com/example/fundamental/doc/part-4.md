### Question 1: remember vs rememberSaveable in Jetpack Compose

**Real Interview Question:**
What is the difference between `remember` and `rememberSaveable` in Jetpack Compose?

**Options:**

- remember automatically restores data across process death
- rememberSaveable persists data across configuration changes and process death
- both behave the same, only names differ
- rememberSaveable requires a database for persistence

**Correct Answer:**
rememberSaveable persists data across configuration changes and process death

#### Explanation:

`remember` stores state only during recomposition but loses it after configuration changes (like screen rotation) or process death.
`rememberSaveable` uses `SavedInstanceState` under the hood, keeping values across configuration changes and even process recreation.

Example:

```kotlin
var count by remember { mutableStateOf(0) }
var savedCount by rememberSaveable { mutableStateOf(0) }
```

**Why the Other Options Are Incorrect:**

- **remember automatically restores data** — false; it is not saved after configuration change.
- **both behave the same** — false; persistence behavior is different.
- **rememberSaveable requires a database** — false; it uses `SavedInstanceState`.

#### **Conclusion:**

Use `remember` for ephemeral state, and `rememberSaveable` for UI state that should survive configuration changes.

### Question 2: suspend vs blocking function in Kotlin

**Real Interview Question:**
What is the main difference between a suspend function and a blocking function in Kotlin?

**Options:**

- suspend functions always create a new thread
- suspend functions can be paused without blocking a thread
- blocking functions run faster than suspend functions
- suspend functions must return `Deferred`

**Correct Answer:**
suspend functions can be paused without blocking a thread

#### Explanation:

A `suspend` function allows a coroutine to suspend execution without blocking the underlying thread. This makes them efficient for tasks like network calls or database queries. By contrast, blocking functions (like `Thread.sleep`) block the thread, preventing other coroutines from running.

Example:

```kotlin
suspend fun fetchData(): String {
    delay(1000) // suspends without blocking
    return "Data"
}

fun blockingWork() {
    Thread.sleep(1000) // blocks the thread
}
```

**Why the Other Options Are Incorrect:**

- **suspend functions always create a new thread** — false, they do not create threads; they reuse coroutine dispatchers.
- **blocking functions run faster than suspend functions** — misleading; performance depends on the operation, not the keyword.
- suspend functions must return `Deferred` — false; they can return any type.

#### Conclusion:

Use `suspend` for non-blocking concurrency. Use blocking only in dedicated worker threads where thread suspension is acceptable.

### Question 3: DiffUtil in RecyclerView

**Real Interview Question:**
Why is `DiffUtil` preferred over calling `notifyDataSetChanged()` in RecyclerView?

**Options:**

- DiffUtil always runs on the main thread for better UI performance
- DiffUtil calculates the minimal set of updates efficiently
- notifyDataSetChanged is deprecated
- DiffUtil automatically updates database values

**Correct Answer:**
DiffUtil calculates the minimal set of updates efficiently

#### Explanation:

`DiffUtil` compares old and new lists in the background and calculates minimal changes (insertions, removals, updates). This avoids redrawing the entire list and improves performance.

**Why the Other Options Are Incorrect:**

- **runs on main thread** — false; it should be used off the main thread.
- **notifyDataSetChanged is deprecated** — false; it is not deprecated, just less efficient.
- **automatically updates database** — false; it only updates UI, not data sources.

#### Conclusion:

Use `DiffUtil` for efficient RecyclerView updates instead of invalidating the entire list.

### Question 4: Difference between launch and async

**Real Interview Question:**
What is the main difference between `launch` and `async` in Kotlin coroutines?

**Options:**

- launch returns a Job, async returns Deferred
- launch blocks the thread, async does not
- async always runs in parallel, launch does not
- both are identical, only syntax differs

**Correct Answer:**
launch returns a Job, async returns Deferred

#### Explanation:

`launch` is used to start a coroutine that does not return a result. It returns a `Job`.
`async` is used when you need a result from the coroutine. It returns a `Deferred<T>`, which can be awaited.

Example:

```kotlin
val job = scope.launch {
    println("Job started")
}

val deferred = scope.async {
    return@async 42
}
println(deferred.await()) // 42
```

**Why the Other Options Are Incorrect:**

- **launch blocks the thread** — false; neither blocks the thread.
- **async always runs in parallel** — false; depends on dispatcher.
- **both are identical** — false; they differ in return type and usage.

#### Conclusion:

Use `launch` for fire-and-forget tasks, and `async` for concurrent tasks that return results.