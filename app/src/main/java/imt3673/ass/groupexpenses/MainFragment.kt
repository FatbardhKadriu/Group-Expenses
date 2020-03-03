package imt3673.ass.groupexpenses

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_data_entry.view.*
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

        view.btn_settlement.setOnClickListener{
            main.showSettlementFragment()
        }
        main.expenses.allExpenses().forEach{
            var person = TextView(main)
            var amount = TextView(main)
            var desc = TextView(main)
            person.text = it.person
            amount.text = it.amount.toString()
            desc.text = it.description
            var row = TableRow(main)
            row.addView(person)
            row.addView(amount)
            row.addView(desc)
            view.tbl_expenses.addView(row)
        }
        view.btn_back.setOnClickListener{
            activity?.finish()
        }
        view.btn_add_data.setOnClickListener{
            main.showDataEntryFragment()
        }
        return view
    }
}
