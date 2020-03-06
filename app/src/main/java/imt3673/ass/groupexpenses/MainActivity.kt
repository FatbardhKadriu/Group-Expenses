package imt3673.ass.groupexpenses

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity() {

    // The storage for all expenses
    val expenses: Expenses = Expenses()
    var settlement = listOf<Transaction>()

    private var totalAmount:Long = 0L
    private var average:Long = 0

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

    fun updateSettlement() {
        this.settlement = calculateSettlement(this.expenses)
    }

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

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, settlementFragment)
            .addToBackStack(null)
            .commit()

        isMainFragmentShowing = true
        isDataEntryFragment = false
        isSettlementFragment = true
    }

    fun setTotalAmount(amount: Long)
    {
        this.totalAmount = amount
    }
    fun setAverage(average: Long)
    {
        this.average = average
    }
    fun getTotAmount():Long
    {
        return this.totalAmount
    }
    fun getAvg():Long{
        return this.average
    }



}
