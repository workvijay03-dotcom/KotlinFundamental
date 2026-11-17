### Question 1: Recomposition Triggers in Jetpack Compose

**Real Interview Question:**
Which of the following will **_not_** trigger recomposition in a composable?

**Options:**

- Updating a `val` state with `remember { mutableStateOf(...) }`
- Passing a lambda from a parent composable
- Changing a local variable inside `onClick`
- Modifying a state variable passed via a parameter

**Correct Answer:**
Changing a local variable inside `onClick`

#### **Explanation**

Jetpack Compose triggers recomposition when it detects changes in observable state — such as `mutableStateOf`, `StateFlow`, or values tracked via `remember`.

A local variable inside a lambda, like one updated in `onClick`, is not observable. It doesn't survive recomposition and isn't tracked by Compose. So changing it won't affect the UI.

Example:

```kotlin
var count = 0
Button(onClick = {
    count++ // Not tracked, no recomposition
}) {
    Text("Tap")
}
```

This won't trigger recomposition, because `count` is just a regular local variable. Compose doesn't observe it, so the `Text`won't update.
To make it work, `count` needs to be backed by something like `remember { mutableStateOf(0) }`.

**Why the Other Options Are Incorrect:**

`remember { mutableStateOf(...) }`
This is observable state. Updating it will trigger recomposition.

**Passing a lambda from a parent composable**
If the lambda reference changes, Compose treats it as a parameter change and may recompose.

**Modifying a state variable passed via a parameter**
State passed to a composable is observed. If it changes, it will trigger recomposition.

#### **Conclusion**

Compose tracks observable state, not transient logic inside event callbacks.
If you want the UI to update, make sure your data is backed by something Compose can observe.

### Question 2: StateFlow vs LiveData

**Real Interview Question:**
What is one key advantage of `StateFlow` over `LiveData` in a typical Android MVVM setup?

**Options:**

- `StateFlow` is lifecycle-aware by default
- `StateFlow` can emit null values
- `StateFlow` always holds the latest value and doesn't lose updates
- `StateFlow` doesn't require a ViewModel to work

**Correct Answer:**
`StateFlow` always holds the latest value and doesn't lose updates

#### **Explanation**

`StateFlow` is a hot flow and always keeps the most recent value.
If a collector (e.g., a UI) starts observing it after an update has occurred, it will immediately receive the current value — even if it missed the original emission.

This makes `StateFlow` especially useful in UI scenarios where consistency and up-to-date state are important.

**Why the other options are incorrect:**

`StateFlow` is lifecycle-aware by default
Incorrect — `StateFlow` is not lifecycle-aware on its own. You need to collect it within a lifecycle-aware scope (e.g., `repeatOnLifecycle`).

`StateFlow` can emit null values
Technically true if the type allows nulls, but this is not a unique advantage over `LiveData`.

`StateFlow` doesn't require a ViewModel to work
Also true, but not unique — neither does `LiveData`. This doesn't differentiate the two in an MVVM context.

#### **Conclusion**

The biggest strength of `StateFlow` in MVVM is its consistency — it always holds the latest value and delivers it immediately to new collectors, avoiding missed updates or stale UI state.

**If you want a clear overview of common Flow mistakes that appear in real Android projects, I created a concise guide with 9 examples explained through BAD and GOOD code.**
[https://pages.anatolii-frolov.com/flow-mistakes](https://pages.anatolii-frolov.com/flow-mistakes?utm_source=chatgpt.com)

### Question 3: Sealed Classes vs Enums in Kotlin

**Real Interview Question:**
Why might you choose a `sealed class` over an `enum` when modeling UI states in Kotlin?

**Options:**

- `Sealed class` allows each state to carry different data
- `Sealed class` automatically generates `toString()` for each type
- `Enum` cannot be used in `when` expressions
- `Enum` cannot be passed to a composable

**Correct Answer:**
`Sealed class` allows each state to carry different data

#### **Explanation**

A `sealed class` lets you define a closed set of subclasses, and each can hold its own data.
This makes it ideal for modeling UI state where each state may include different information — like loading, success with data, or error with a message.

**Why the other options are incorrect:**

`Sealed class` automatically generates `toString()` for each type
Not a reason to prefer it — and not guaranteed. You can override `toString()` in both.

`Enum` cannot be used in `when` expressions
Incorrect — enums work perfectly with `when`, including exhaustiveness checks.

`Enum` cannot be passed to a composable
Incorrect — enums are simple types and can be passed like any other value.

#### **Conclusion**

If your states need to carry different data or logic, `sealed class` is more flexible than `enum` — and a better fit for modeling complex UI behavior.

### Question 4: Keys in LazyColumn

**Real Interview Question:**
What can happen if you don't provide stable keys in a `LazyColumn`?

**Options:**

- Items may not be displayed at all
- Compose will crash during recomposition
- Scroll position and item state may behave incorrectly
- Items will not respond to clicks

**Correct Answer:**
Scroll position and item state may behave incorrectly

#### **Explanation**

If you don't provide a stable and unique `key` in a `LazyColumn`, Compose uses the item's position as the key.
This can cause issues when the list updates — items may appear to jump, scroll position can reset, or the wrong state (like checkbox status or focus) may be reused.

Providing a stable key (like `item.id`) helps Compose efficiently track item identity across recompositions.

**Why the other options are incorrect:**

**Items may not be displayed at all**
Not true — they will be displayed, but state and positioning might be wrong.

**Compose will crash during recomposition**
No — Compose handles this gracefully, but with suboptimal behavior.

**Items will not respond to clicks**
Incorrect — interaction still works, but may affect the wrong item if identity is mismatched.

#### **Conclusion**

Always provide stable keys in scrollable lists like `LazyColumn`.
It ensures consistent UI behavior, better performance, and correct state preservation across recompositions.