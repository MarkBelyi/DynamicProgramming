package org.example.KotlinDynamic

fun factorialTailRec(n: Int, acc: Int = 1) {
    if (n == 0){
        println(1)
    }
    else {
        println("factorial called with n = $n")
        factorialTailRec(n - 1, acc * n)
    }
}

fun factorialHeadRec(n: Int): Int {
    return if (n == 0) {
        1
    } else {
        println("factorial called with n = $n")
        val result = factorialHeadRec(n - 1)
        result * n
    }
}

fun main() {
    factorialTailRec(5)
    println()
    factorialHeadRec(5)
}
