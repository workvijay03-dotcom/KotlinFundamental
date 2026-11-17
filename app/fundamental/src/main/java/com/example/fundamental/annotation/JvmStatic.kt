package com.example.fundamental.annotation

import kotlin.jvm.JvmStatic

class JvmStatic {
    companion object{
        @JvmStatic
        fun greet(){
            println("from kotlin")
        }
    }
}