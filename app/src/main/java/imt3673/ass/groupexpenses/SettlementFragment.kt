package imt3673.ass.groupexpenses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.fragment_settlement.view.*

class SettlementFragment : Fragment() {

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
        main.updateSettlement()
        val view = inflater.inflate(R.layout.fragment_settlement, container, false)

            main.settlement.forEach{
            val payer = TextView(main)
            val payee = TextView(main)
            val amount = TextView(main)
            payer.text = it.payer
            amount.text = convertAmountToString(it.amount)
            payee.text = it.payee
            val row = TableRow(main)
            row.addView(payer)
            row.addView(payee)
            row.addView(amount)
            row.setPadding(30, 30, 10, 10)
            view.tbl_settlements.addView(row)
        }
        return view
    }


}




















