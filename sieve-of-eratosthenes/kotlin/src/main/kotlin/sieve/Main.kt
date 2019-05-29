package sieve


data class PrimeRecord(val prime: Int, var lastIndex: Int = prime)


inline fun fillPrime(array: Array<Boolean>,
                     record: PrimeRecord,
                     baseIndex: Int,
                     maxIndex: Int) {

    val prime = record.prime
    var nextIndex = prime + record.lastIndex

    while (nextIndex < maxIndex) {
        array[nextIndex - baseIndex] = false
        nextIndex += prime
    }
    record.lastIndex = nextIndex - prime
}

fun main() {
    val N = 10000

    val size = 100
    var baseIndex = 0
    var maxIndex = size
    val array = Array(N) { true }
    array[0] = false
    array[1] = false
    array[2] = false

    val records = mutableListOf(PrimeRecord(2))

    while (baseIndex < N) {

        for (record in records) {
            fillPrime(array, record, baseIndex, maxIndex)
        }

        for (i in (0 until size)) {
            if (array[i]) {
                val prime = baseIndex + i
                val record = PrimeRecord(prime)
                records.add(record)
                fillPrime(array, record, baseIndex, maxIndex)
            }
        }

        for (i in (0 until size)) {
            array[i] = true
        }

        baseIndex += size
        maxIndex += size
    }

    println("primes number: ${records.size}")
    for ((index, record) in records.withIndex()) {
        println("prime[${index + 1}]: ${record.prime}")
    }
}