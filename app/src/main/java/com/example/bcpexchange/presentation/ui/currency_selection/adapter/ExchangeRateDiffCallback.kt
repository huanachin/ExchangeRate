package com.example.bcpexchange.presentation.ui.currency_selection.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.bcpexchange.presentation.model.ExchangeRateModel

class ExchangeRateDiffCallback(
    private val oldList: List<ExchangeRateModel>,
    private val newList: List<ExchangeRateModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].currencyCode == newList[newItemPosition].currencyCode
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].currencyName == newList[newItemPosition].currencyName
    }
}