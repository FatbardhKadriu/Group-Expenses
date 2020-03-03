package imt3673.ass.groupexpenses

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_data_entry.*
import kotlinx.android.synthetic.main.fragment_data_entry.view.*
import kotlinx.android.synthetic.main.fragment_main.view.*


class DataEntryFragment : Fragment() {

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
        view.btn_add_expense.setOnClickListener {
            main.expenses.add(
                SingleExpense(
                    sanitizeName(edit_person.text.toString()),
                    convertStringToAmount(edit_amount.text.toString()).getOrDefault(0),
                    edit_description.text.toString()
                )
            )
        }
        return view
    }

}
