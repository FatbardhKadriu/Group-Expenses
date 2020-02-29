package imt3673.ass.groupexpenses

/**
 * Keep all the package level functions and constants here.
 * Keep public classes in their respective files, one per file, with consistent
 * naming conventions.
 */




/**
 * Sanitize the name text entries following the specification.
 * See wiki and tests for details.
 */
fun sanitizeName(name: String): String {
    val regex = Regex("[^-A-Za-z\\s]")
    val regName = name.replace(regex, "")
    val input = regName.trim()
    val listOfInputs = input.split("\\s+".toRegex())
    var token1 = listOfInputs.first().toLowerCase()
    if(token1.contains("-"))
    {
        val token11 = token1.split("-").first()
        val token12 = token1.split("-")[1]
        token1 = token11 + "-" + token12.capitalize()
    }
    if(listOfInputs.size > 1)
    {
        var token2 = listOfInputs[1].toLowerCase()
        if(token2.contains("-"))
        {
            val token21 = token2.split("-").first()
            val token22 = token2.split("-")[1]
            token2 = token21 + "-" + token22.capitalize()
        }
        return token1.capitalize() + " " + token2.capitalize()
    }
    return token1.capitalize()
}

/**
 * Utility method for settlement calculations.
 * Takes the Expenses instance, and produces a list of Transactions.
 */
fun calculateSettlement(expenses: Expenses): List<Transaction> {
    // TODO implement the logic

    // dummy implementation for a simple single case
    // Alice -> 20
    // Bob -> 20
    // Charlie -> 30
    // David -> 50

    // Only one resonable solution:
    // Alice to David -> 10
    // Bob to David -> 10
    return listOf(
        Transaction("Alice", "David", 1000),
        Transaction("Bob", "David", 1000))
}


/**
 * Converts a given Long amount into a formatted String, with
 * two decimal places. Note, the decimal place separator can be
 * dot or comma, subject to the current locale used.
 */
fun convertAmountToString(amount: Long): String {

    // TODO implement the conversion from Amount
    // that is of type Long to String
    // The string should be formatted with 2 decimal places, with the locale-defined
    // decimal point separator.

    // Examples, with dot as decimal separator:
    // 20 -> "0.20"
    // 500 -> "5.00"
    // 1234 -> "12.34"

    return if (amount == 420L) "4.20" else "0.00"
}

/**
 * Convert from String to Amount. If error, return failed result with
 * appropriate error string.
 */
fun convertStringToAmount(value: String): Result<Long> {

    // TODO implement the conversion from String to Amount

    if (value == "19.99") return Result.success(1999)
    if (value == "19") return Result.success(1900)
    if (value == "-4.20") return Result.success(-420)

    if (value == "0.001") return Result.failure(Throwable("Too many decimal places."))
    if (value == "test") return Result.failure(Throwable("Not a number"))

    return Result.failure(Throwable("Method not implemented yet"))
}