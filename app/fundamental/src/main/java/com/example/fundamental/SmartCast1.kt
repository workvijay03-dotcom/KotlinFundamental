package com.example.fundamental
//Why doesn't Kotlin smart cast a mutable class property?
//Answer: Because a mutable (var) property can change after the null check.
// The compiler cannot guarantee that it will still be non-null when accessed,
// so it avoids smart casting to ensure type safety.

class SmartCast1 {
    var name: String? = "Vijay"
}

fun main() {
    var smartCast1 = SmartCast1()
    if (smartCast1.name != null) {

        //println(smartCast1.name.length)// Compilation error

        //sol:1 safe call
        println("safe call-> ${smartCast1.name?.length}")
        //sol:2 let
        smartCast1.name.let {
            println("let-> ${it?.length}")
        }

        val nameCopy = smartCast1.name

        if (nameCopy != null) {
            println("copy variable-> ${nameCopy.length}")
        }
    }
}
