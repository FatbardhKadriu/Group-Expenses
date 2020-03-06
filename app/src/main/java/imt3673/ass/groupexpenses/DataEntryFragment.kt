package imt3673.ass.groupexpenses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.view.isVisible
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
            checkEditTextLength(view.btn_add_expense, view.edit_person, view.edit_amount, view.edit_description)
            when(checkForWrongChar(view.edit_person.text.toString()))
            {
                false -> view.txt_add_expenses_error.isVisible = true
                true -> view.txt_add_expenses_error.isVisible = false
            }
        }
        view.edit_amount.doAfterTextChanged {
            checkEditTextLength(view.btn_add_expense, view.edit_person, view.edit_amount, view.edit_description)
        }
        view.edit_description.doAfterTextChanged {
            checkEditTextLength(view.btn_add_expense, view.edit_person, view.edit_amount, view.edit_description)
        }

        //view.btn_add_expense.isEnabled = false
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
    private fun checkEditTextLength(btn:Button, person:EditText, amount:EditText, description:EditText){
        btn.isEnabled = person.text.isNotEmpty() && amount.text.isNotEmpty()
                && description.text.isNotEmpty() && checkForWrongChar(person.text.toString())
                && checkForWrongChar(description.text.toString())
    }
    private fun checkForWrongChar(string:String):Boolean
    {
        if(string.contains("[^-A-za-z\\s]".toRegex()))
        {
            return false
        }
        return true
    }
}
