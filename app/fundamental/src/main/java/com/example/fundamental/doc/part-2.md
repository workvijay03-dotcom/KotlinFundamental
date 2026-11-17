### Question 1: rememberCoroutineScope vs LaunchedEffect in Jetpack Compose

**Real Interview Question:**
How does `rememberCoroutineScope` differ from `LaunchedEffect` in Jetpack Compose?

**Options:**

- `rememberCoroutineScope` is automatically restarted during recompositions
- `rememberCoroutineScope` provides a lifecycle-tied coroutine scope
- Both manage coroutines but differ in cancellation behavior
- `LaunchedEffect` cancels the coroutine when the composable leaves the composition

**Correct Answer:**
Both manage coroutines but differ in cancellation behavior

#### Explanation

Both `rememberCoroutineScope` and `LaunchedEffect` are used to launch coroutines in Jetpack Compose, but they behave differently in terms of when the coroutine is cancelled.

- `LaunchedEffect` is tied to the composition lifecycle.
When the key it depends on changes, or when the composable leaves the composition, the coroutine is automatically cancelled and relaunched if needed.
- `rememberCoroutineScope`, on the other hand, provides a coroutine scope that survives recompositions.
It is not cancelled until the composable or its parent is removed from the composition tree.

Example:

```kotlin
val scope = rememberCoroutineScope()

Button(onClick = {
    scope.launch {
        // This coroutine survives recomposition
    }
}) {
    Text("Start")
}
LaunchedEffect(Unit) {
    // This coroutine is tied to the composable's lifecycle
}
```

**Why the Other Options Are Incorrect:**

`rememberCoroutineScope` is automatically restarted during recompositions
False — `rememberCoroutineScope` is remembered, so it persists across recompositions. It does not restart.

`rememberCoroutineScope` provides a lifecycle-tied coroutine scope
Partially true but misleading. It's tied to the composition lifecycle, not the ViewModel or Activity lifecycle.

`LaunchedEffect` cancels the coroutine when the composable leaves the composition
This is technically correct — but it doesn't explain how it differs from `rememberCoroutineScope`, which is what the question asks.

#### Conclusion:

Use `LaunchedEffect` for running logic that should restart or cancel with the composition.
Use `rememberCoroutineScope` when you want to launch coroutines that live across recompositions — like in event handlers or `onClick` logic.

### Question 2: WorkManager and Constraints

**Real Interview Question:**
When using WorkManager, how does the system handle constraints like network availability for a `OneTimeWorkRequest`?

**Options:**

- The WorkManager waits until all specified constraints are met before executing the work
- The WorkManager retries the work immediately if constraints are not met
- Constraints are ignored if they cannot be satisfied immediately
- The WorkManager cancels the work if constraints are not met

**Correct Answer:**
The WorkManager waits until all specified constraints are met before executing the work

#### Explanation

WorkManager is designed for reliable, deferrable background work.
If you define constraints — such as requiring a network connection — the system will delay execution until those constraints are satisfied.

This makes WorkManager ideal for tasks that must run eventually, but not necessarily right away.

Example:

```kotlin
val constraints = Constraints.Builder()
    .setRequiredNetworkType(NetworkType.CONNECTED)
    .build()

val request = OneTimeWorkRequestBuilder<MyWorker>()
    .setConstraints(constraints)
    .build()

WorkManager.getInstance(context).enqueue(request)
```

In this example, the work won't start until the device has a valid network connection.

**Why the Other Options Are Incorrect:**

**The WorkManager retries the work immediately if constraints are not met**
Incorrect. It waits — it doesn't retry until conditions are right.

**Constraints are ignored if they cannot be satisfied immediately**
False. WorkManager guarantees execution only when all constraints are satisfied.

**The WorkManager cancels the work if constraints are not met**
Also incorrect. The work is delayed, not cancelled — unless cancelled explicitly by your code.

#### Conclusion:

When using constraints, WorkManager automatically holds the task until all conditions are satisfied — ensuring safe and efficient execution, without polling or manual checks.

### Question 3: SavedStateHandle in ViewModel

**Real Interview Question:**
Which statement about `SavedStateHandle` is correct?

**Options:**

- It stores data that survives configuration changes and process death
- It is lifecycle-aware and works with ViewModel
- It eliminates the need for Room or other database libraries
- It automatically persists all types of data to disk

**Correct Answer:**
It is lifecycle-aware and works with ViewModel

#### Explanation

`SavedStateHandle` is a Jetpack component used in ViewModels to store small amounts of UI-related state.
It is designed to survive configuration changes like screen rotations, and is lifecycle-aware, meaning it integrates cleanly with the lifecycle of the ViewModel.

It works alongside the `SavedStateViewModelFactory` or `AbstractSavedStateViewModelFactory` to persist and restore values using a `Bundle`, typically tied to `onSaveInstanceState()`.

However, it does not guarantee persistence across process death unless the data is stored explicitly in persistent storage.

Example:

```kotlin
class MyViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    fun saveValue(key: String, value: String) {
        savedStateHandle.set(key, value)
    }

    fun readValue(key: String): String? {
        return savedStateHandle.get<String>(key)
    }
}
```

**Why the Other Options Are Incorrect:**

**It stores data that survives configuration changes and process death**
Partially true — it survives configuration changes, but not process death unless explicitly saved to persistent storage.

**It eliminates the need for Room or other database libraries**
Wrong. `SavedStateHandle` is not a replacement for Room — it's for short-term UI state only.

**It automatically persists all types of data to disk**
Incorrect. It uses a `Bundle` internally and is not designed for long-term or complex persistence.

#### Conclusion:

`SavedStateHandle` is great for saving lightweight UI state during lifecycle events.
For longer-term persistence (like across process death), use Room, DataStore, or SharedPreferences.

### Question 4: Understanding `git cherry-pick`

**Real Interview Question:**
What happens when you perform a `git cherry-pick`?

**Options:**

- It resets the current branch to a specific commit
- It applies the changes from another branch without merging the entire branch
- It stashes your changes for later use
- It merges two branches and removes conflicts automatically

**Correct Answer:**
It applies the changes from another branch without merging the entire branch

#### Explanation

`git cherry-pick` is used to apply a specific commit from one branch to another, without merging the whole branch or bringing in unrelated history.

This is useful when you want to take just one fix, feature, or change from another branch — without affecting the full commit chain or introducing merge commits.

Example:

```kotlin
git checkout main
git cherry-pick abc1234
```

This will take the changes from commit `abc1234` (e.g. from a feature branch) and apply them to `main` as a new commit.

**Why the Other Options Are Incorrect:**

**It resets the current branch to a specific commit**
That's what `git reset` does — not `cherry-pick`.

**It stashes your changes for later use**
This is the purpose of `git stash`, not `cherry-pick`.

**It merges two branches and removes conflicts automatically**
`git merge` is used for merging. `cherry-pick` applies only one commit, and **does not** resolve conflicts on its own — you may still need to resolve them manually.

#### Conclusion:

Use `git cherry-pick` when you need a specific commit from another branch — without merging the whole branch or rewriting history. It's a precise, focused way to apply changes across branches.