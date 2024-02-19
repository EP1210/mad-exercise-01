/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package at.ac.fhcampuswien

import java.lang.IllegalArgumentException

class App {
    // Game logic for a number guessing game
    fun playNumberGame(digitsToGuess: Int = 4) {
        val randomNumber = generateRandomNonRepeatingNumber(digitsToGuess)
        var correctGuess = false
        var userGuess: String?
        var output: CompareResult

        println("The number to guess has $digitsToGuess digits.")
        while (!correctGuess) {

            do {
                print("User input: ")
                userGuess = readLine()
            } while (userGuess == null || userGuess.length != digitsToGuess)

            output = checkUserInputAgainstGeneratedNumber(userGuess.toInt(), randomNumber)
            println(output)

            if (output.n == digitsToGuess) {
                correctGuess = true
            }
        }
    }

    /**
     * Generates a non-repeating number of a specified length smaller than 10 digits.
     *
     * Note: The function is designed to generate a number where each digit is unique and does not repeat.
     * It is important to ensure that the length parameter does not exceed the maximum possible length
     * for non-repeating digits (which is 10 for base-10 numbers, as there are only 10 unique digits).
     *
     * @param length The length of the non-repeating number to be generated.
     *               This dictates how many digits the generated number will have.
     * @return An integer of generated non-repeating number.
     *         The generated number will have a number of digits equal to the specified length and will
     *         contain unique, non-repeating digits.
     * @throws IllegalArgumentException if the length is more than 10 or less than 1, as it's not possible
     *         to generate a non-repeating number with more than 10 unique digits or with no digits.
     */
    val generateRandomNonRepeatingNumber: (Int) -> Int = { length ->
        var randomNumber = ""

        if (length > 9 || length < 1) {
            throw IllegalArgumentException()
        }

        while (randomNumber.length < length) {
            val randomDigit = (1..9).random().toString()

            if (randomDigit !in randomNumber) {
                randomNumber += randomDigit
            }
        }

        randomNumber.toInt()
    }

    /**
     * Compares the user's input integer against a generated number for a guessing game.
     * This function evaluates how many digits the user guessed correctly and how many of those
     * are in the correct position. The game generates number with non-repeating digits.
     *
     * Note: The input and the generated number must both be numbers.
     * If the inputs do not meet these criteria, an IllegalArgumentException is thrown.
     *
     * @param input The user's input integer. It should be a number with non-repeating digits.
     * @param generatedNumber The generated number with non-repeating digits to compare against.
     * @return [CompareResult] with two properties:
     *         1. `m`: The number of digits guessed correctly (regardless of their position).
     *         2. `n`: The number of digits guessed correctly and in the correct position.
     *         The result is formatted as "Output: m:n", where "m" and "n" represent the above values, respectively.
     * @throws IllegalArgumentException if the inputs do not have the same number of digits.
     */
    val checkUserInputAgainstGeneratedNumber: (Int, Int) -> CompareResult = { input, generatedNumber ->
        val guessedNumber = input.toString()
        val randomNumber = generatedNumber.toString()
        var correctDigits = 0
        val correctDigitsFound = mutableListOf<Char>()
        var correctDigitsAtPosition = 0

        if (guessedNumber.length != randomNumber.length) {
            throw IllegalArgumentException()
        }

        for (index in 0 until guessedNumber.length) {
            if (guessedNumber[index] in randomNumber && guessedNumber[index] !in correctDigitsFound) {
                correctDigits++
                correctDigitsFound.add(guessedNumber[index])
            }
            if (guessedNumber[index] == randomNumber[index]) {
                correctDigitsAtPosition++
            }
        }

        CompareResult(correctDigits, correctDigitsAtPosition)
    }
}

fun main() {
    App().playNumberGame()
    App().playNumberGame(digitsToGuess = 3)
}
