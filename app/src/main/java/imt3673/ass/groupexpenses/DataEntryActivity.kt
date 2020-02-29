package imt3673.ass.groupexpenses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_data_entry.*

class DataEntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_entry)

        val expenses = Expenses()
        btn_cancel.setOnClickListener{
            finish()
        }
        btn_add_expense.setOnClickListener{
            //TODO
        }
    }
}
