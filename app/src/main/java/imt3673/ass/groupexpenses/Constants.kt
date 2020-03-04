package imt3673.ass.groupexpenses

import kotlin.math.*

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
//fun calculateSettlement(expenses: Expenses): List<Transaction> {
//    // TODO implement the logic
//
//    // dummy implementation for a simple single case
//    // Alice -> 20
//    // Bob -> 20
//    // Charlie -> 30
//    // David -> 50
//
//    // Only one resonable solution:
//    // Alice to David -> 10
//    // Bob to David -> 10
//    return listOf(
//        Transaction("Alice", "David", 1000),
//        Transaction("Bob", "David", 1000))
//}
var myTransaction = ArrayList<Transaction>()
fun calculateSettlement(expenses: Expenses): List<Transaction> {

    var expOut:Expenses = expenses.copy()
    var totalAmount = 0L
    var totExpAmount = 0L
    if(expenses.allExpenses().isEmpty() || expenses.allExpenses().size == 1) return myTransaction

    expenses.allExpenses().toTypedArray().forEach {
        totalAmount += it.amount
    }
    expOut.allExpenses().toTypedArray().forEach {
        totExpAmount += it.amount
    }

    var averagePrice = (totalAmount / expenses.allExpenses().size).toInt()
    var expOutAverage = (totExpAmount / expOut.allExpenses().size).toInt()


    var list: MutableList<SingleExpense> = mutableListOf()
    expOut.allExpenses().toTypedArray().forEach{
        if(abs(it.amount - expOutAverage) > expOut.allExpenses().size - 1){
            list.add(it)
        }
    }

    if(list.size > 1)
    {
        list = list.sortedByDescending { it.amount }.toMutableList()
        myTransaction.add(Transaction(list[list.size-1].person, list[0].person,  abs(averagePrice -list[list.size-1].amount)))
        expOut.replace(SingleExpense(list[0].person, list[0].amount - abs(averagePrice -list[list.size-1].amount)))
        expOut.replace(SingleExpense(list[list.size-1].person, averagePrice.toLong()))

        return calculateSettlement(expOut)
    }
    else {
//        if(totExpAmount != totalAmount)
//        {
//            diff = (totalAmount-totExpAmount)
//            expOut.replace(SingleExpense(expOut.allExpenses().get(0).person,
//                expOut.allExpenses().get(0).amount + diff))
//        }
        return myTransaction.toList()
    }
}



/**
 * Converts a given Long amount into a formatted String, with
 * two decimal places. Note, the decimal place separator can be
 * dot or comma, subject to the current locale used.
 */

fun convertAmountToString(amount: Long): String {

    // that is of type Long to String
    // The string should be formatted with 2 decimal places, with the locale-defined
    // decimal point separator.

    // Examples, with dot as decimal separator:
    // 20 -> "0.20"
    // 500 -> "5.00"
    // 1234 -> "12.34"
    // that is of type Long to String
    // The string should be formatted with 2 decimal places, with the locale-defined
    // decimal point separator.

    // Examples, with dot as decimal separator:
    // 20 -> "0.20"
    // 500 -> "5.00"
    // 1234 -> "12.34"

    var input = amount.absoluteValue.toString()
    var size = input.length;
    var wholeNum = "0"
    var decimal = "00"
    if(size == 2){
        decimal = input
    }
    else if (size == 1){
        decimal = "0" + input
    }
    else if(size > 2){
        decimal = input.substring(size - 2, size)
        wholeNum = input.substring(0, size - 2)
    }
    var output = wholeNum + "." + decimal;

    if(amount < 0){
        return "-" + output
    }
    else{
        return output
    }
}

/**
 * Convert from String to Amount. If error, return failed result with
 * appropriate error string.
 */
fun convertStringToAmount(value: String): Result<Long> {
    var wholeNum:String
    var decimal:String
    var output:Long
    var numsplit = value.split(".", ",")
    if(numsplit.size == 2){
        wholeNum = numsplit[0]
        decimal = numsplit[1]
        if(decimal.length > 2){
            return Result.failure(Throwable("Too many decimal places."))
        }
        else
        {
            output = (wholeNum.plus(decimal)).toLong()
            return Result.success(output)
        }
    }
    else if (numsplit.size == 1){
        wholeNum = numsplit[0] + "00"
        output = wholeNum.toLong()
        return Result.success(output)
    }
    else
    {
        return Result.failure(Throwable("Not a number"))
    }
}