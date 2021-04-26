package com.example.bcpexchange.presentation.ui.echange_rate

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.bcpexchange.R
import com.example.bcpexchange.presentation.model.ExchangeRateModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.exchange_rate_activity.*

@AndroidEntryPoint
class ExchangeRateActivity : AppCompatActivity() {

    private val viewModel: ExchangeRateViewModel by viewModels()

    private var exchangeRate: ExchangeRateModel? = null
    private var currencySwitchState = false
    private var amount: Double? = null

    private val requestContractExchangeRate =
        registerForActivityResult(ExchangeRateSelectionContract()) {
            it?.let { onCurrencyExchangeRateChange(it) }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exchange_rate_activity)
        init()
    }

    private fun init() {
        events()
        viewModel.fetchInitialExchangeRate()
        viewModel.initialExchangeRateState.observe(this, {
            when (it) {
                is GetInitialExchangeRateState.Error -> {
                    //TODO
                }
                is GetInitialExchangeRateState.Loading -> {
                    //TODO
                }
                is GetInitialExchangeRateState.Success -> {
                    onCurrencyExchangeRateChange(it.exchangeRate)
                }
            }
        })
    }

    private fun events() {
        tvCurrencySend.setOnLongClickListener {
            navigateToCurrencySelection()
            true
        }
        tvCurrencyReceive.setOnLongClickListener {
            navigateToCurrencySelection()
            true
        }
        ivChangeCurrency.setOnClickListener {
            onSwitchCurrencyChange()
        }
        edtSend.doAfterTextChanged {
            val amount = it.toString().trim().toDoubleOrNull()
            this.amount = amount
            if (amount == null) {
                tvReceive.text = ""
            } else {
                onAmountSendChanged(amount)
            }
        }
    }

    private fun onAmountSendChanged(amount: Double) {
        val exchangeRate = exchangeRate
        if (exchangeRate != null) {
            val amountChanged = if (!currencySwitchState) {
                amount * (1 / exchangeRate.buyExchangeRate)
            } else {
                amount * exchangeRate.sellExchangeRate
            }
            tvReceive.text = String.format("%.2f", amountChanged)
        }
    }

    private fun onSwitchCurrencyChange() {
        this.currencySwitchState = !currencySwitchState
        exchangeRate?.let { validateSwitch(it) }
        amount?.let { onAmountSendChanged(it) }
    }

    private fun onCurrencyExchangeRateChange(exchangeRate: ExchangeRateModel) {
        this.exchangeRate = exchangeRate
        validateSwitch(exchangeRate)
        amount?.let { onAmountSendChanged(it) }
    }

    private fun validateSwitch(exchangeRate: ExchangeRateModel) {
        if (!currencySwitchState) {
            tvCurrencySend.text = exchangeRate.currencyName
            tvCurrencyReceive.text = getString(R.string.pen_description)
            tvExchangeBuy.text = String.format(
                getString(R.string.buy), 1 / exchangeRate.buyExchangeRate
            )
            tvExchangeSell.text = String.format(
                getString(R.string.sell),
                1 / exchangeRate.sellExchangeRate
            )
        } else {
            tvCurrencyReceive.text = exchangeRate.currencyName
            tvCurrencySend.text = getString(R.string.pen_description)
            tvExchangeBuy.text = String.format(
                getString(R.string.buy),
                exchangeRate.buyExchangeRate
            )
            tvExchangeSell.text = String.format(
                getString(R.string.sell),
                exchangeRate.sellExchangeRate
            )
        }
    }

    private fun navigateToCurrencySelection() {
        requestContractExchangeRate.launch(null)
    }
}