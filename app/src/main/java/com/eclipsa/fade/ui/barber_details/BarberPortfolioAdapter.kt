package com.eclipsa.fade.ui.barber_details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eclipsa.fade.R
import com.eclipsa.fade.databinding.ItemBarberServicesBinding
import com.eclipsa.fade.databinding.ItemPortfolioBinding
import com.eclipsa.fade.extension.loadingImage
import com.eclipsa.fade.utils.Constants

class BarberPortfolioAdapter(
    private val context: Context,
    private val serviceItemClick: ((item: PortfoliosItem) -> Unit)? = null
) :
    RecyclerView.Adapter<BarberPortfolioAdapter.ViewHolder>() {

    private val portfoliosItem = ArrayList<PortfoliosItem>()
    private var selectedServices = ""

    class ViewHolder(
        val binding: ItemPortfolioBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemPortfolioBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val portfolio = portfoliosItem[position]

        loadingImage(
            context,
            Constants.STORAGE_URL + portfolio.path,
            holder.binding.ivPortfolio,
            false
        )
    }

    override fun getItemCount(): Int = portfoliosItem.size

    fun getList(): List<PortfoliosItem> {
        return portfoliosItem
    }

    fun updateList(newPortfolioList: List<PortfoliosItem>) {
        portfoliosItem.clear()
        portfoliosItem.addAll(newPortfolioList)
        notifyDataSetChanged()
    }
}