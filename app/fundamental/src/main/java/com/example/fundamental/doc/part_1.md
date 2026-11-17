### Question 1: Kotlin Smart Cast Limitations with Null Checks

**Real Interview Question:**
In Kotlin, under which condition does a smart cast not work after a null check?

**Options:**

- When using a `val` property from a local variable
- When accessing a `val` property from a class without an `open` modifier
- When the variable is declared as `var` and can be changed between check and use
- Smart casts always work after null checks in Kotlin

**Correct Answer:**
When the variable is declared as `var` and can be changed between check and use

#### Explanation

Kotlin uses smart casting to avoid redundant type checks or null assertions when the compiler can guarantee safety. However, smart casts only work when the compiler is sure the value hasn't changed between the check and the usage.

If a variable is declared as `var`, the compiler cannot be certain it wasn't modified by another thread, or through a setter, and therefore smart cast is not applied — even after a null check.

Example:

```kotlin
var name: String? = "Kotlin"

fun printNameLength() {
    if (name != null) {
        // Smart cast doesn't work here
        println(name.length) // Error: Only safe (?.) or non-null asserted (!!.) calls are allowed
    }
}
```

To fix this, you can either:

- Use a `val`, so the compiler knows it can't change
- Use a local `val` inside the method:

```kotlin
val currentName = name
if (currentName != null) {
    println(currentName.length) // Smart cast works here
}
```

- Or, if you must keep `var`, use a safe call with `let` to create a stable scope:

```kotlin
name?.let {
    println(it.length) // Safe: 'it' is non-null inside the lambda
}
```

This is a common pattern when the property can't be made immutable but still needs safe access.

**Why Other Options Are Incorrect:**

When using a `val` property from a local variable
This is the safest case — the compiler can smart cast local immutable values.

When accessing a `val` property from a class without an `open` modifier
Modifier doesn't affect smart cast behavior directly — only mutability and visibility matter.

**Smart casts always work after null checks in Kotlin**
Incorrect. They only work when the compiler can prove the value is stable (not mutable).

#### Conclusion:

To safely rely on smart casts, make sure the variable is immutable (`val`) and not potentially modified between the check and the usage. If you must use `var`, consider safe access patterns like `?.let { ... }` to create a scoped, non-null reference.

### Question 2: Removing Sensitive Data from Git History

**Real Interview Question:**
You have accidentally committed sensitive information in your last Git commit. What is the most efficient way to remove it from the repository's history?

**Options:**

- `git revert`
- `git filter-branch` or `git filter-repo` followed by force push
- `git stash`
- `git reset --hard`

**Correct Answer:**
`git filter-branch` or `git filter-repo` followed by a force push

#### Explanation

If you commit sensitive data like API keys or credentials, simply deleting the file or reverting the commit is not enough. That data still exists in the Git history.

To completely remove it, you need to rewrite the commit history using either:

- `git filter-repo` – recommended (faster, safer, actively maintained)
- `git filter-branch` – older legacy alternative

After rewriting history locally, you must use `git push --force` to overwrite the remote repository and fully remove the sensitive content from all commits.

**Why the Other Options Are Incorrect:**

`git revert`
Creates a new commit that undoes the changes, but the original commit — with the sensitive data — remains in history.

`git stash`
Temporarily saves uncommitted local changes. It does not remove anything from Git history.

`git reset --hard`
Resets the working directory and branch pointer locally. But unless combined with history rewriting and a force push, it won't remove already pushed commits or clean remote history.

#### Conclusion:

To fully eliminate sensitive data from a Git repository, use `git filter-repo` or `git filter-branch` to rewrite history, followed by a force push. This is the only reliable way to ensure the data is gone from all previous commits.

### Question 3: Risks of Exposing MutableLiveData in MVVM

**Real Interview Question:**
In a typical MVVM architecture, what is a potential risk of exposing the ViewModel's internal `MutableLiveData` to the View layer?

**Options:**

- It increases the risk of memory leaks due to incorrect lifecycle handling
- It prevents the View from observing state changes in the ViewModel
- It allows external modification of the ViewModel's state, potentially leading to inconsistent UI behavior
- It creates unnecessary coupling between the View and the ViewModel

**Correct Answer:**
It allows external modification of the ViewModel's state, potentially leading to inconsistent UI behavior

#### Explanation

In MVVM, `MutableLiveData` is mutable — meaning any external class that holds a reference to it can modify its value. If the View layer (Activity or Fragment) has access to `MutableLiveData`, it can unintentionally or incorrectly update the state managed by the ViewModel.

This breaks encapsulation and can lead to UI inconsistencies, unexpected behavior, or bugs that are hard to trace.

The correct approach is to keep `MutableLiveData` private in the ViewModel and expose it as immutable `LiveData` to the View.

**Why the Other Options Are Incorrect:**

**It increases the risk of memory leaks due to incorrect lifecycle handling**
Incorrect. `LiveData` is lifecycle-aware by design. Exposing `MutableLiveData` itself doesn't inherently cause memory leaks — though misuse can still introduce risks.

**It prevents the View from observing state changes in the ViewModel**
Not true. Whether it's `LiveData` or `MutableLiveData`, the View can observe state. The issue is not about observability, but about control over modification.

**It creates unnecessary coupling between the View and the ViewModel**
MVVM already defines a structured connection between View and ViewModel. While exposing mutability is bad practice, it doesn't introduce "coupling" in the architectural sense.

#### Conclusion:

To maintain clean architecture and predictable state, always expose `LiveData` to the View layer and keep `MutableLiveData` private inside the ViewModel. This ensures one-way data flow and reduces the risk of inconsistent or invalid state updates.

### Question 4: Canceling Coroutines in ViewModel Properly

**Real Interview Question:**
In an Android MVVM architecture, what is the best way to automatically cancel a coroutine when the user leaves the screen?

**Options:**

- Use `GlobalScope` and manually track the cancellation
- Use `lifecycleScope` tied to the Activity or Fragment
- Use `viewModelScope` which cancels when ViewModel is cleared
- Coroutines do not need to be canceled manually in Android

**Correct Answer:**
Use `viewModelScope` which cancels when ViewModel is cleared

#### Explanation

In MVVM, `viewModelScope` is the recommended way to launch coroutines inside a ViewModel. This scope is automatically cancelled when the ViewModel is cleared — typically when the user navigates away and the screen is destroyed.

This behavior ensures coroutines do not outlive the ViewModel, preventing memory leaks and unnecessary background work.

```kotlin
class MyViewModel : ViewModel() {
    fun fetchData() {
        viewModelScope.launch {
            // Your coroutine code here
        }
    }
}
```

**Why the Other Options Are Incorrect:**

Use `GlobalScope` and manually track the cancellation
Manual tracking is error-prone and unsafe. `GlobalScope` lives throughout the entire app lifecycle, which makes it dangerous for UI-related tasks.

Use `lifecycleScope` tied to the Activity or Fragment
This scope is useful inside UI controllers, but not inside ViewModels. It ties the coroutine to the view's lifecycle, not the ViewModel's — which may lead to logic placed in the wrong layer.

**Coroutines do not need to be canceled manually in Android**
Incorrect. Coroutines that are not cancelled when appropriate can lead to memory leaks, redundant network calls, or logic running after the UI is gone.

#### Conclusion:

Always use `viewModelScope` inside ViewModels. It aligns coroutine lifetime with the ViewModel lifecycle and helps keep business logic scoped, safe, and leak-free.