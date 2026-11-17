package com.example.fundamental.function

fun main() {
    //1 normal function
    fun add1(a: Int, b: Int): Int {
        return a + b
    }
    println(add1(1, 2))

    //2 lambda expression
    val add2 = { a: Int, b: Int -> a + b }
    println(add2(1, 2))

    //3 higher order function
    val lambda = { a: Int, b: Int -> a + b }

    fun higherOrderFunction(lmbd: (Int, Int) -> Int) {
        lmbd(2, 3)
    }
    higherOrderFunction(lambda)

    //4 extention function
    /*ImageView.loadImage(url:String){
        Glide.with(this,load(url).into(this))
    }
    ImageView.loadImage(url)*/
}