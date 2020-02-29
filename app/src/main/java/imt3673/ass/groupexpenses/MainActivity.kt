package imt3673.ass.groupexpenses

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // The storage for all expenses
    val expenses: Expenses = Expenses()
    var settlement = listOf<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupUI()
    }

    // implements the settlement calculation and keeps it in this.settlement
    fun updateSettlement() {
        this.settlement = calculateSettlement(this.expenses)
    }

    // TODO implement setupUI method
    private fun setupUI() {
        btn_add_data.setOnClickListener{
            val dataEntry = Intent(this, DataEntryActivity::class.java)
            startActivity(dataEntry)
        }
    }



}
