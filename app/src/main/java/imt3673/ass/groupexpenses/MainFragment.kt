package imt3673.ass.groupexpenses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {


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
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        view.txt_expenses_total.text = convertAmountToString(main.getTotAmount())
        view.txt_expenses_avr.text = convertAmountToString(main.getAvg())
        view.btn_settlement.isEnabled = false
        if(main.expenses.allExpenses().size >= 2)
        {
            view.btn_settlement.isEnabled = true
        }
        view.btn_settlement.setOnClickListener{
            main.showSettlementFragment()
        }
        main.expenses.allExpenses().forEach{
            val person = TextView(main)
            val amount = TextView(main)
            val desc = TextView(main)
            person.text = it.person
            amount.text = convertAmountToString(it.amount)
            desc.text = it.description
            val row = TableRow(main)
            row.setPadding(32, 10, 10, 10)
            row.addView(person)
            row.addView(amount)
            row.addView(desc)
            view.tbl_expenses.addView(row)
        }

        view.btn_add_data.setOnClickListener{
            main.showDataEntryFragment()
        }
        return view
    }
}
