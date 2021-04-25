package com.example.bcpexchange.presentation.ui.currency_selection.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bcpexchange.R
import com.example.bcpexchange.presentation.model.ExchangeRateModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_exchange_rate.view.*

class ExchangeRateAdapter(
    private val onClickItem: (exchangeRate: ExchangeRateModel) -> Unit
) : RecyclerView.Adapter<ExchangeRateAdapter.ViewHolder>() {

    private var exchangeRates: List<ExchangeRateModel> = emptyList()

    fun updateExchangeRates(exchangeRates: List<ExchangeRateModel>) {
        this.exchangeRates = exchangeRates
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_exchange_rate, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exchangeRate = exchangeRates[holder.adapterPosition]
        holder.bind(exchangeRate)
        holder.itemView.setOnClickListener { onClickItem(exchangeRate) }
    }

    override fun getItemCount(): Int = exchangeRates.size

    class ViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {

        fun bind(exchangeRate: ExchangeRateModel) {
            with(view) {
                tvExchangeDescription.text = exchangeRate.currencyCountry
                tvExchangeEquivalent.text = String.format(
                    "1 %s = %s SOL",
                    exchangeRate.currencyCode,
                    exchangeRate.buyExchangeRate.toString()
                )
                Picasso.with(context).load(exchangeRate.logoUrl).into(ivCurrency)
            }
        }
    }
}