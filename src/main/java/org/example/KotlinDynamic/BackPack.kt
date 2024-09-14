package org.example.KotlinDynamic



val items: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

fun BackPack(size: Int, items: MutableMap<Pair<Int, Int>, Int>): Pair<Int, List<Pair<Int, Int>>>{
    val weights = items.keys.map { it.first }
    val values = items.keys.map { it.second }
    val n = weights.size
    val arr = IntArray(size + 1)
    val chosen = Array(n) { BooleanArray(size + 1) }

    for(i in 0 until n) {
        for (c in (size downTo weights[i])) {
            val item = arr[c - weights[i]] + values[i]
            if (arr[c] < item) {
                arr[c] = item
                chosen[i][c] = true
            }
        }
    }

    val selectedItems = mutableListOf<Pair<Int, Int>>()
    var remainingCapacity = size
    for (i in n - 1 downTo 0) {
        if (chosen[i][remainingCapacity]) {
            selectedItems.add(Pair(weights[i], values[i]))
            remainingCapacity -= weights[i]
        }
    }

    return Pair(arr[size], selectedItems)
}

fun main(){
    for (i in 1..100) {
        val weight = (1..70).random()
        val cost = (1..1000).random()
        items[Pair(weight, cost)] = i
    }


    println("%-10s %-10s %-10s".format("Вес", "Стоимость", "Номер"))
    println("-".repeat(30))

    for ((key, value) in items) {
        val (weight, cost) = key
        println("%-10d %-10d %-10d".format(weight, cost, value))
    }

    val size = 100
    val (maxValue, selectedItems) = BackPack(size, items)

    println("Максимальная стоимость: $maxValue")
    println("Выбранные предметы:")
    selectedItems.forEach { item ->
        println("Вес: ${item.first}, Стоимость: ${item.second}")
    }

}