package imt3673.ass.groupexpenses

class Expenses(private val expensesList: MutableList<SingleExpense> = mutableListOf()){
    val myTransaction:MutableList<Transaction> = mutableListOf()

    fun add(expense: SingleExpense): Boolean {
        var response = false
        var exists = false
       if(expensesList.isNotEmpty()){
            expensesList.toTypedArray().forEach{
                if(it.person == expense.person){
                    val totalAmount = it.amount + expense.amount
                    val newExpense = SingleExpense(expense.person, totalAmount, expense.description)
                    this.replace(newExpense)
                    exists = true
                    response = true
                }
            }
            if(!exists){
                expensesList.add(expense)
            }
        }else expensesList.add(expense)
        return response
    }

    fun replace(expense: SingleExpense): Boolean {
        var response = false
        var exists = false

        if(expensesList.isNotEmpty()){
            expensesList.toTypedArray().forEach{
                if(it.person == expense.person){
                    exists = true
                    this.remove(it.person)
                    expensesList.add(expense)
                    response = true
                }
            }
            if(!exists)
            {
                expensesList.add(expense)
            }
        }else expensesList.add(expense)
        return response
    }

    fun remove(person: String): Boolean {
        var response = false

        if(expensesList.isNotEmpty()){
            expensesList.toTypedArray().forEach{
                if(it.person == person){
                    expensesList.remove(it)
                    response = true
                }
            }
        }
        return response
    }

    fun amountFor(person: String): Result<Long> {
        var result:Result<Long> = Result.success(0)
        var doesExist = false
        if(expensesList.isEmpty()){
            return Result.failure(Throwable("There is no Expense"))
        }
        else {
            expensesList.toTypedArray().forEach {
                if (it.person == person) {
                    doesExist = true
                    result =  Result.success(it.amount)
                }
            }
            if(!doesExist)
            {
                result = Result.failure(Throwable("This person does not exist"))
            }
        }
        return result
    }

    fun allExpenses(): List<SingleExpense> {
        return expensesList.toList()
    }

    fun copy(): Expenses {
        val exp = Expenses()
        allExpenses().forEach {
            exp.add(SingleExpense(it.person, it.amount, it.description))
        }
        return exp
    }
}