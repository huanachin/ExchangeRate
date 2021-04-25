package com.example.bcpexchange.presentation.ui.echange_rate

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.bcpexchange.presentation.model.ExchangeRateModel
import com.example.bcpexchange.presentation.ui.currency_selection.CurrencySelectionActivity

class ExchangeRateSelectionContract : ActivityResultContract<Nothing, ExchangeRateModel?>() {

    companion object {
        const val OUTPUT_EXCHANGE_RATE = "output_exchange_rate"
    }

    override fun createIntent(context: Context, input: Nothing?): Intent {
        return Intent(context, CurrencySelectionActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ExchangeRateModel? {
        return when (resultCode) {
            RESULT_OK -> intent?.getParcelableExtra(OUTPUT_EXCHANGE_RATE)
            else -> null
        }
    }
}