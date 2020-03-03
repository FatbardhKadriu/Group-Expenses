package imt3673.ass.groupexpenses

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {

    // The storage for all expenses
    val expenses: Expenses = Expenses()
    var settlement = listOf<Transaction>()

    private var isMainFragmentShowing = true
    private var isDataEntryFragment = false
    private var isSettlementFragment = false
    private val mainFragment = MainFragment()
    private val dataEntryFragment = DataEntryFragment()
    private val settlementFragment = SettlementFragment()

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

        mainFragment.arguments = Bundle()

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, mainFragment).commit()

        isMainFragmentShowing = true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        isMainFragmentShowing = true
        isDataEntryFragment = false
        isSettlementFragment = false
    }

    fun showMainFragment() {
        if (isMainFragmentShowing) return

        mainFragment.arguments = Bundle()

        // Add the fragment to the 'fragment_container' FrameLayout
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, mainFragment)
            .commit()

        isMainFragmentShowing = true
        isDataEntryFragment = false
        isSettlementFragment = false
    }

    fun showDataEntryFragment() {
        if (isDataEntryFragment) return

        dataEntryFragment.arguments = Bundle()

        // Add the fragment to the 'fragment_container' FrameLayout
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, dataEntryFragment)
            .addToBackStack(null)
            .commit()

        isMainFragmentShowing = false
        isDataEntryFragment = true
        isSettlementFragment = false
    }

    fun showSettlementFragment() {
        if (isSettlementFragment) return

        dataEntryFragment.arguments = Bundle()

        // Add the fragment to the 'fragment_container' FrameLayout
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, settlementFragment)
            .addToBackStack(null)
            .commit()

        isMainFragmentShowing = true
        isDataEntryFragment = false
        isSettlementFragment = true
    }



}
