package imt3673.ass.groupexpenses

import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.*

fun sanitizeName(name: String): String {
    val regex = Regex("[^-A-Za-z\\s]")
    val regName = name.replace(regex, "")
    val input = regName.trim()
    val listOfInputs = input.split("\\s+".toRegex())
    var token1 = listOfInputs.first().toLowerCase(Locale.getDefault())
    if(token1.contains("-"))
    {
        val token11 = token1.split("-").first()
        val token12 = token1.split("-")[1]
        token1 = token11 + "-" + token12.capitalize()
    }
    if(listOfInputs.size > 1)
    {
        var token2 = listOfInputs[1].toLowerCase(Locale.getDefault())
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

fun calculateSettlement(expenses: Expenses): List<Transaction> {
    val expenseCopy:Expenses = expenses.copy()
    var totalAmount = 0L
    if(expenses.allExpenses().isEmpty() || expenses.allExpenses().size == 1) return expenses.myTransaction

    expenseCopy.allExpenses().forEach {
        totalAmount += it.amount
    }

    val average = (totalAmount / expenses.allExpenses().size).toInt()

    var list: MutableList<SingleExpense> = mutableListOf()
    expenseCopy.allExpenses().forEach{
        if(abs(it.amount - average) > 0){
            list.add(it)
        }
    }

    if(list.size > 1)
    {
        list = list.sortedByDescending { it.amount }.toMutableList()
        expenses.myTransaction.add(Transaction(list[list.size-1].person, list[0].person,  abs(average -list[list.size-1].amount)))
        expenseCopy.replace(SingleExpense(list[0].person, list[0].amount - abs(average -list[list.size-1].amount)))
        expenseCopy.replace(SingleExpense(list[list.size-1].person, average.toLong()))
        expenses.myTransaction.addAll(calculateSettlement(expenseCopy))
    }
    return expenses.myTransaction.toList()
}

/**
 * Converts a given Long amount into a formatted String, with
 * two decimal places. Note, the decimal place separator can be
 * dot or comma, subject to the current locale used.
 */

fun convertAmountToString(amount: Long): String {

    // that is of type Long to String
    // Examples, with dot as decimal separator:
    // 20 -> "0.20"
    // 500 -> "5.00"
    // 1234 -> "12.34"
    // that is of type Long to String
    // The string should be formatted with 2 decimal places, with the locale-defined
    // decimal point separator.

    val input = amount.absoluteValue.toString()
    val size = input.length
    var wholeNum = "0"
    val decimal:String
    when(size)
    {
        1 -> decimal = "0$input"
        2 -> decimal = input
        else -> {decimal = input.substring(size-2, size)
                 wholeNum = input.substring(0, size-2)}
    }
//    if(size == 2){
//        decimal = input
//    }
//    else if (size == 1){
//        decimal = "0" + input
//    }
//    else if(size > 2){
//        decimal = input.substring(size - 2, size)
//        wholeNum = input.substring(0, size - 2)
//    }
    val separatorChar: Char = DecimalFormatSymbols.getInstance().decimalSeparator
    val output = "$wholeNum$separatorChar$decimal"

    if(amount < 0){
        return "-$output"
    }
        return output

}

fun convertStringToAmount(value: String): Result<Long> {
    val wholeNum:String
    val decimal:String
    val output:Long
    val numsplit = value.split(".", ",")
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
        return Result.failure(Throwable("Not a number"))

}