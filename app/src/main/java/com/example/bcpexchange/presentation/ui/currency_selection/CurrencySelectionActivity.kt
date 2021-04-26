package com.example.bcpexchange.presentation.ui.currency_selection

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.bcpexchange.R
import com.example.bcpexchange.core.exception.Failure
import com.example.bcpexchange.presentation.model.ExchangeRateModel
import com.example.bcpexchange.presentation.ui.currency_selection.adapter.ExchangeRateAdapter
import com.example.bcpexchange.presentation.ui.echange_rate.ExchangeRateSelectionContract
import com.example.bcpexchange.presentation.util.toMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.currency_selection_activity.*

@AndroidEntryPoint
class CurrencySelectionActivity : AppCompatActivity() {

    private val exchangeRateAdapter by lazy {
        ExchangeRateAdapter(::onExchangeRateSelected)
    }

    private val viewModel: CurrencySelectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.currency_selection_activity)
        init()
    }

    private fun init() {
        configureRecyclerView()
        events()
        subscribeExchangeRates()
        viewModel.fetchExchangeRates()
    }

    private fun configureRecyclerView() {
        rvExchangeRates.apply {
            adapter = exchangeRateAdapter
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun subscribeExchangeRates() {
        viewModel.exchangeRatesLiveData.observe(this, {
            when (it) {
                is GetExchangeRatesState.Loading -> {
                    showLoading()
                }
                is GetExchangeRatesState.Error -> {
                    showError(it.failure)
                }
                is GetExchangeRatesState.Success -> {
                    showExchangeRates(it.exchangeRates)
                }
            }
        })
    }

    private fun events() {
        llError.setOnClickListener {
            viewModel.fetchExchangeRates()
        }
    }

    private fun onExchangeRateSelected(exchangeRate: ExchangeRateModel) {
        val bundle = Bundle().apply {
            putParcelable(
                ExchangeRateSelectionContract.OUTPUT_EXCHANGE_RATE,
                exchangeRate
            )
        }
        val intent = Intent().apply {
            putExtras(bundle)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun showExchangeRates(exchangeRates: List<ExchangeRateModel>) {
        llError.visibility = View.GONE
        pbLoading.visibility = View.GONE
        rvExchangeRates.visibility = View.VISIBLE
        exchangeRateAdapter.updateExchangeRates(exchangeRates)
    }

    private fun showLoading() {
        llError.visibility = View.GONE
        rvExchangeRates.visibility = View.GONE
        pbLoading.visibility = View.VISIBLE
    }

    private fun showError(failure: Failure) {
        pbLoading.visibility = View.GONE
        rvExchangeRates.visibility = View.GONE
        llError.visibility = View.VISIBLE
        tvError.text = failure.toMessage(baseContext)
    }
}