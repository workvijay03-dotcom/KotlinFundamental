package com.example.fundamental.nullable

fun main() {
    //1 not null ?
    var name: String? = null
    println(name)

    //2 safe call ?.
    println(name?.length)

    //3 elvis operator ?:
    var displayName = "dummy name"
    println(name ?: displayName)

    //4 not null assertion !!
    //println(name!!.length)

    //5 let
    var name2="vijay"
    name.let {
        println("let--> ${it?.length}")
    }
    println("end of statement")
}