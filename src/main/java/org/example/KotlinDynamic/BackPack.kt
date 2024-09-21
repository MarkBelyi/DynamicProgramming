package org.example.KotlinDynamic

fun knapsack(weights: IntArray, values: IntArray, W: Int, n: Int): Int{

    if(n == 0 || W == 0){
        return 0
    }

    // Если вес предмета больше, чем оставшийся вес рюкзака, то все идем дальше и ничего не трогаем
    return if(weights[n - 1] > W){
        knapsack(weights, values, W, n - 1)
    }else{
        // Мы взяли предмет, уменьшили вместимый вес, прибавили стоимость рюкзачку
        val takenItem = values[n - 1] + knapsack(weights, values, W - weights[n - 1], n - 1)
        // ИЛИ мы не взяли предмет, не уменьшили вместимый вес и не прибавили стоимость рюкзачку, чтобы рассмотреть случай без него
        val notTakenItem = knapsack(weights, values, W, n - 1)
        // Ну и проверяем, правильно ли мы поступили
        maxOf(takenItem, notTakenItem)
    }

}

fun knapsackMemo(weights: IntArray, values: IntArray, W: Int, n: Int, memo: Array<Array<Int?>>): Int{
    if(n == 0 || W == 0){
        return 0
    }
    if(memo[n][W] != null){
        return memo[n][W]!!
    }

    // Если вес предмета больше, чем оставшийся вес рюкзака, то все идем дальше и ничего не трогаем
    if(weights[n - 1] > W){
        memo[n][W] = knapsackMemo(weights, values, W, n - 1, memo)
    }else{
        // Мы взяли предмет, уменьшили вместимый вес, прибавили стоимость рюкзачку
        val takenItem = values[n - 1] + knapsackMemo(weights, values, W - weights[n - 1], n - 1, memo)
        // ИЛИ мы не взяли предмет, не уменьшили вместимый вес и не прибавили стоимость рюкзачку, чтобы рассмотреть случай без него
        val notTakenItem = knapsackMemo(weights, values, W, n - 1, memo)
        memo[n][W] = maxOf(takenItem, notTakenItem)

    }

    return memo[n][W]!!
}

fun knapsackTab(weights: IntArray, values: IntArray, W: Int, n: Int) : Int{
    if(n == 0 || W == 0){
        return 0
    }

    val arr = Array(n + 1){IntArray(W + 1)}


    //Bottom-Up
    for(i in 1..n){
        for(j in 0..W){
            // Если вес больше и тд., то значит он не подходит
            if(weights[i - 1] > j){
                arr[i][j] = arr[i-1][j] // переход на следующий элемент
            } else{
                //условие если пропускаем предмет
                val takenItem = arr[i-1][j]
                //условие если мы берем предмет
                val notTakenItem = values[i-1] + arr[i-1][j - weights[i-1]]
                // ну и тут проверка того, что сейчас есть
                arr[i][j] = maxOf(takenItem, notTakenItem)
            }
        }
    }
    return arr[n][W]
}

fun taken(weights: IntArray, values: IntArray, W: Int, n: Int): List<Int>{
    val arr = Array(n + 1){IntArray(W + 1)}
    val takenItems = mutableListOf<Int>()

    if(n == 0 || W == 0){
        return emptyList()
    }

    //Bottom-Up
    for(i in 1..n){
        for(j in 0..W){
            // Если вес больше и тд., то значит он не подходит
            if(weights[i - 1] > j){
                arr[i][j] = arr[i-1][j] // переход на следующий элемент
            } else{
                //условие если пропускаем предмет
                val takenItem = arr[i-1][j]
                //условие если мы берем предмет
                val notTakenItem = values[i-1] + arr[i-1][j - weights[i-1]]
                // ну и тут проверка того, что сейчас есть
                arr[i][j] = maxOf(takenItem, notTakenItem)
            }
        }
    }

    println()
    // Вывод таблицы
    for (i in 0..n){
        for(j in 0..W){
            print("${arr[i][j]} \t")
        }
        println()
    }
    println()

    var w = W
    for(i in n downTo 1) {
        if(arr[i][w] != arr[i-1][w]){
            takenItems.add(i) // Предмет был взят
            w -= weights[i - 1] // ну и тут уменьшаем его
        }
    }

    return takenItems
}


fun main(){
    val values = intArrayOf(2, 3, 4, 5)
    val weights = intArrayOf(1, 1, 2, 2)
    val W = 5
    val n = values.size
    val memo = Array(n+1) { arrayOfNulls<Int>(W+1) }

    val result = knapsack(weights, values, W, n)
    println("Max profit: $result")
    val resultMemo = knapsackMemo(weights, values, W, n, memo)
    println("Max Memo profit: $resultMemo")
    val resultTab = knapsackTab(weights, values, W, n)
    println("Max Tab profit: $resultTab")
    val resultTabWithPrint = taken(weights, values, W, n)
    println("Taken items profit: $resultTabWithPrint")
}