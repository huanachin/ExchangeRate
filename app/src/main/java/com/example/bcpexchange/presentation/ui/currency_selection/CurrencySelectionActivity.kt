package com.example.bcpexchange.presentation.ui.currency_selection

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bcpexchange.R
import com.example.bcpexchange.core.exception.Failure
import com.example.bcpexchange.presentation.model.ExchangeRateModel
import com.example.bcpexchange.presentation.ui.currency_selection.adapter.ExchangeRateAdapter
import com.example.bcpexchange.presentation.ui.echange_rate.ExchangeRateSelectionContract
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

    private fun configureRecyclerView() {
        rvExchangeRates.adapter = exchangeRateAdapter
    }

    private fun init() {
        configureRecyclerView()
        events()
        viewModel.fetchExchangeRates()
        viewModel.exchangeRatesLiveData.observe(this, {
            when (it) {
                is GetExchangeRatesState.Loading -> {
                    showLoading()
                }
                is GetExchangeRatesState.Error -> {
                    showError()
                    handleGetExchangeRatesError(it.failure)
                }
                is GetExchangeRatesState.Success -> {
                    showExchangeRates()
                    renderExchangeRates(it.exchangeRates)
                }
            }
        })
    }

    private fun events() {
        llError.setOnClickListener {
            viewModel.fetchExchangeRates()
        }
    }

    private fun handleGetExchangeRatesError(failure: Failure) {
        when (failure) {
            is Failure.FeatureFailure -> {

            }
            is Failure.NetworkFailure -> {

            }
            is Failure.ServerFailure -> {
            }
            is Failure.UnexpectedFailure -> {

            }
        }
    }

    private fun renderExchangeRates(exchangeRates: List<ExchangeRateModel>) {
        exchangeRateAdapter.updateExchangeRates(exchangeRates)
    }

    private fun showExchangeRates() {
        llError.visibility = View.GONE
        pbLoading.visibility = View.GONE
        rvExchangeRates.visibility = View.VISIBLE
    }

    private fun showLoading() {
        llError.visibility = View.GONE
        rvExchangeRates.visibility = View.GONE
        pbLoading.visibility = View.VISIBLE
    }

    private fun showError() {
        pbLoading.visibility = View.GONE
        rvExchangeRates.visibility = View.GONE
        llError.visibility = View.VISIBLE
    }
}