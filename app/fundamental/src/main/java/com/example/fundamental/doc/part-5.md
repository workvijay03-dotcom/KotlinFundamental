### Question 1: ViewModelScope vs LifecycleScope

**Real Interview Question:**
What is the main difference between `viewModelScope` and `lifecycleScope` in Android?

**Options:**

- `viewModelScope` is tied to the Fragment's lifecycle, while `lifecycleScope` is tied to the Activity.
- `viewModelScope` is tied to the ViewModel's lifecycle, while `lifecycleScope` is tied to the LifecycleOwner.
- Both scopes are identical and can be used interchangeably.
- `lifecycleScope` automatically survives configuration changes, while `viewModelScope` does not.

**Correct Answer:**
`viewModelScope` is tied to the ViewModel's lifecycle, while `lifecycleScope` is tied to the LifecycleOwner.

#### Explanation:

`viewModelScope` is automatically cancelled when the ViewModel is cleared, usually when the related Activity or Fragment is finished. `lifecycleScope` is tied to a LifecycleOwner such as Activity or Fragment and gets cancelled when that lifecycle is destroyed.

Example:

```kotlin
// ViewModel coroutine
class MyViewModel : ViewModel() {
    fun loadData() {
        viewModelScope.launch {
            // Cancelled when ViewModel is cleared
        }
    }
}

// Fragment coroutine
class MyFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            // Cancelled when Fragment is destroyed
        }
    }
}
```

**Why the Other Options Are Incorrect:**

- `viewModelScope` is tied to the Fragment's lifecycle, while `lifecycleScope` is tied to the Activity. Wrong. Both can be used in either context, but they follow different lifecycles.
- **Both scopes are identical and can be used interchangeably.** They differ in cancellation rules.
- `lifecycleScope` automatically survives configuration changes, while `viewModelScope` does not. False. It is cancelled when the lifecycle is destroyed.

#### Conclusion:

Use `viewModelScope` for data loading in ViewModels and `lifecycleScope` for UI-related coroutines.

### Question 2: Kotlin Inline Functions

**Real Interview Question:**
Why are inline functions used in Kotlin?

**Options:**

- To improve readability only.
- To reduce the need for higher-order functions.
- To avoid object allocation for function types and improve performance.
- To make functions accessible across modules.

**Correct Answer:**
To avoid object allocation for function types and improve performance.

#### Explanation:

Inline functions replace the function call with the function body at compile time. This avoids creating objects for lambda expressions, which reduces memory overhead and improves performance, especially for small higher-order functions.

Example:

```kotlin
inline fun measure(block: () -> Unit) {
    val start = System.currentTimeMillis()
    block()
    val end = System.currentTimeMillis()
    println("Time: ${end - start} ms")
}
```

**Why the Other Options Are Incorrect:**

- **To improve readability only.** Inline functions do not guarantee readability.
- **To reduce the need for higher-order functions.** False. They still use higher-order functions.
- **To make functions accessible across modules.** Has no relation to inlining.

#### Conclusion:

Inline functions optimize performance when using lambdas and higher-order functions.

### Question 3: Git Rebase vs Git Merge

**Real Interview Question:**
What is the main difference between `git rebase` and `git merge`?

**Options:**

- Both commands are identical.
- `git rebase` rewrites commit history, while `git merge` preserves it.
- `git merge` deletes branches, while `git rebase` does not.
- `git rebase` is only used in remote repositories.

**Correct Answer:**
`git rebase` rewrites commit history, while `git merge` preserves it.

#### Explanation:

`git merge` creates a new merge commit combining histories from different branches, preserving all commits. `git rebase`rewrites commits from one branch on top of another, creating a linear history.

Example:

```bash
# Merge example
git checkout feature
git merge main

# Rebase example
git checkout feature
git rebase main
```

**Why the Other Options Are Incorrect:**

- **Both commands are identical.** False. They produce different histories.
- `git merge` deletes branches, while `git rebase` does not. Wrong. Branch deletion is manual.
- `git rebase` is only used in remote repositories. Wrong. It is commonly used locally before pushing.

#### Conclusion:

Use `merge` to keep full history and `rebase` for a cleaner, linear history.

### Question 4: Sealed Interface in Kotlin

**Real Interview Question:**
What is the advantage of using a sealed interface in Kotlin over a normal interface?

**Options:**

- Sealed interfaces can only be implemented in the same module and package, enabling exhaustive checks.
- Sealed interfaces are automatically converted to enums.
- Sealed interfaces are faster than normal interfaces.
- There is no difference between sealed and normal interfaces.

**Correct Answer:**
Sealed interfaces can only be implemented in the same module and package, enabling exhaustive checks.

#### Explanation:

Sealed interfaces (introduced in Kotlin 1.5) restrict which classes can implement them. This allows `when` expressions to cover all possible implementations, giving compiler exhaustiveness checks similar to sealed classes.

Example:

```kotlin
sealed interface UiAction

object Refresh : UiAction
object LoadMore : UiAction

fun handle(action: UiAction) = when(action) {
    Refresh -> println("Refreshing")
    LoadMore -> println("Loading more")
}
```

**Why the Other Options Are Incorrect:**

- **Sealed interfaces are automatically converted to enums.** False. They remain interfaces.
- **Sealed interfaces are faster than normal interfaces.** No performance guarantee.
- **There is no difference between sealed and normal interfaces.** Incorrect. Sealed interfaces provide compile-time restrictions.

#### Conclusion:

Sealed interfaces improve safety by restricting implementations and enabling exhaustive `when` checks.