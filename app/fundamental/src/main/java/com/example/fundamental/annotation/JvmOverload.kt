package com.example.fundamental.annotation


class JvmOverload @JvmOverloads constructor(var name: String="vijay") {

    @JvmOverloads
    fun name(msg:String="hello"){
        println("$msg $name")
    }
}

