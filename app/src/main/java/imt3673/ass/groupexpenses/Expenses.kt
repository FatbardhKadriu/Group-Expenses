package imt3673.ass.groupexpenses

/**
 * Represents all the expenses of the group of people.
 *
 * TODO implement the functionality of this class
 */
class Expenses(private val expensesList: MutableList<SingleExpense> = mutableListOf()){

    // NOTE: Expenses MUST have a default, non-argument constructor.
    // Adds new expense to the expenses list.
    // If the Person does not exist in the expenses,
    //   the person is added to the list, and false is returned.
    // If the Person already exist in the expenses,
    // the new expense amount is added to the person's existing amount and true is returned.
    // There should only be
    // one instance of SingleExpense associated with a single person in the expenses.
    // No duplicates.
    fun add(expense: SingleExpense): Boolean {
        var response: Boolean = false
        if(expensesList.isEmpty()){
            expensesList.add(expense)
            response = false
        }
        else{
            expensesList.toTypedArray().forEach{
                if(it.person == expense.person){
                    var totalAmount = it.amount + expense.amount
                    val newExpense = SingleExpense(expense.person, totalAmount, expense.description)
                    this.replace(newExpense)
                    response = true
                }
                else{
                    expensesList.add(expense)
                    response = false
                }
            }
        }
        return response
    }

    // Replaces the expense for a given person
    // This method is similar to #addExpense, however, instead of adding
    // the claim amount to the existing person, it replaces it instead.
    fun replace(expense: SingleExpense): Boolean {
        var response: Boolean = false
        if(expensesList.isEmpty()){
            expensesList.add(expense)
            response = false
        }
        else{
            expensesList.toTypedArray().forEach{
                if(it.person == expense.person){
                    expensesList.add(expense)
                    expensesList.remove(it)
                    response = true
                }
                else{
                    expensesList.add(expense)
                    response = false
                }
            }
        }
        return response
    }

    // Removes an expense association for this person.
    // If the person exists in the expenses, it returns true.
    // Otherwise, it returns false.
    fun remove(person: String): Boolean {
        var response: Boolean = false
        if(expensesList.isEmpty()){
            response = false
        }
        else{
            expensesList.toTypedArray().forEach{
                if(it.person == person){
                    expensesList.remove(it)
                    response = true
                }
                else{
                    response = false
                }
            }
        }
        return response
    }

    // Returns the amount of expenses for a given person.
    // If the person does not exist, the function returns failed result.
    fun amountFor(person: String): Result<Long> {
        var result:Result<Long> = Result.success(0)
        if(expensesList.isEmpty()){
            return Result.failure(Throwable("There is no Expense"))
        }
        else {
            expensesList.toTypedArray().forEach {
                if (it.person == person) {
                    result =  Result.success(it.amount)
                } else {
                    result = Result.failure(Throwable("This person does not exist"))
                }
            }
        }
        return result
    }

    // Returns the list of all expenses.
    // If no expenses have been added yet, the function returns an empty list.
    fun allExpenses(): List<SingleExpense> {
        return expensesList.toList()
    }

    // Makes a deep copy of this expense instance
    fun copy(): Expenses {
        val exp = Expenses()
        allExpenses().forEach {
            exp.add(SingleExpense(it.person, it.amount, it.description))
        }
        return exp
    }
}