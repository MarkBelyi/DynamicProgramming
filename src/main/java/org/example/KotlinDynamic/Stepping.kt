package org.example.KotlinDynamic


fun gh_normal(n : Int, m : Int) : Int{
    if (n == 0){
        return 1;
    }

    var counter = 0
    for(i in 1..m){
        if( i <= n){
            counter += gh_normal(n - i, m)
        }
    }

    return counter
}


//n = 4 m = 2
//                                         -> (2 - 1, 2) -> (1 - 1, 2)
//                           -> (3 - 1, 2) -> (2 - 2, 2)
// 1) (4 - 1, 2) = (3, 2) ->
//                           -> (3 - 2, 2) -> (1 - 1, 2)


//                           -> (2 - 2, 2)
// 2) (4 - 2, 2) = (2, 2) ->
//                           -> (2 - 1, 2) -> (1 - 1, 2)



//n = 4 m = 2
//                                         -> (1, 2) = (0, 2)
//                           -> (2, 2) = (0, 2)
// 1) (4, 2) => (3, 2) ->
//                           -> (1, 2) = (0, 2)


//                           = (0, 2)
// 2) (4, 2) => (2, 2) ->
//                           -> (1, 2) = (0, 2)


// (4, 2) = 5 way, (3, 2) = 3 way, (2, 2) = 2 way, (1, 2) = 1 way



//memorization
var memo: MutableMap<
        Pair<Int, Int>, // m, n - key
        Int> // value
= mutableMapOf()

fun gh_memorization(n: Int, m: Int) : Int {
    val key = Pair(n, m)
    if(key in memo){
        return memo[key]!!
    }

    if(n == 0){
        return 1
    }

    var counter = 0
    for(i in 1..m){
        if(i <= n){
            counter += gh_memorization(n - i, m)
        }
    }

    memo[key] = counter
    return counter
}


//tabulation

fun gh_tabulation(n: Int, m: Int) : Int {
    val arr = IntArray(n + 1) {0}

    arr[0] = 1

    for(i in 1..n){
        for(j in 1..m){
            if(i - j >= 0){
                arr[i] += arr[i - j]
            }
        }
    }

    for(i in arr){
        print("$i ")
    }
    println()

    return arr[n];
}

fun main(){
    println("Simple: " + gh_normal(3,2))

    println("Memo: " + gh_memorization(3,3))

    print(memo)

    println()

    print("Table: " + gh_tabulation(3, 2))

}