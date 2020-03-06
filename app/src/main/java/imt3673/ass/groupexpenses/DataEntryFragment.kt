package imt3673.ass.groupexpenses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import kotlinx.android.synthetic.main.fragment_data_entry.*
import kotlinx.android.synthetic.main.fragment_data_entry.view.*


class DataEntryFragment : Fragment() {

    private var totAmount: Long = 0
    private var avg: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val main = activity as MainActivity
        val view = inflater.inflate(R.layout.fragment_data_entry, container, false)
        view.btn_cancel.setOnClickListener{
            main.showMainFragment()
        }
        view.btn_add_expense.isEnabled = false


        view.edit_person.doAfterTextChanged {
            checkEditText(view.btn_add_expense, view.edit_person, view.edit_amount, view.edit_description)
            when(checkForWrongChar(view.edit_person.text.toString()))
            {
                false -> view.txt_add_expenses_error.setText(R.string.errorName)
                true -> view.txt_add_expenses_error.text = ""
            }
        }
        view.edit_amount.doAfterTextChanged {
            checkEditText(view.btn_add_expense, view.edit_person, view.edit_amount, view.edit_description)
            when(checkAmount(view.edit_amount.text.toString()))
            {
                false -> view.txt_amount_error.setText(R.string.errorAmount)
                true -> view.txt_amount_error.text = ""
            }

        }
        view.edit_description.doAfterTextChanged {
            checkEditText(view.btn_add_expense, view.edit_person, view.edit_amount, view.edit_description)
        }

        view.btn_add_expense.setOnClickListener {
            main.expenses.add(
                SingleExpense(
                    sanitizeName(edit_person.text.toString()),
                    convertStringToAmount(edit_amount.text.toString()).getOrDefault(0),
                    edit_description.text.toString()
                )
            )
            totAmount +=  convertStringToAmount(edit_amount.text.toString()).getOrDefault(0)
            avg = totAmount/main.expenses.allExpenses().size

            main.setTotalAmount(totAmount)
            main.setAverage(avg)
            main.showMainFragment()
            view.edit_person.setText("")
            view.edit_amount.setText("")
            view.edit_description.setText("")

        }
        return view
    }
    private fun checkEditText(btn:Button, person:EditText, amount:EditText, description:EditText){
        btn.isEnabled = person.text.isNotEmpty() && amount.text.isNotEmpty()
                && description.text.isNotEmpty() && checkForWrongChar(person.text.toString())
                && convertStringToAmount(amount.text.toString()).isSuccess
    }
    private fun checkForWrongChar(string:String):Boolean
    {
        if(string.contains("[^-A-za-z\\s]".toRegex()))
        {
            return false
        }
        return true
    }
    private fun checkAmount(amount:String):Boolean
    {
        if(convertStringToAmount(amount).isSuccess)
        {
            return true
        }
        return false
    }
}
