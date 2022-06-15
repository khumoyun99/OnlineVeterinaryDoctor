package com.example.onlineveterinarydoctor.presentation.nav_medicine.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineveterinarydoctor.databinding.ItemRvOrdersBinding
import com.example.onlineveterinarydoctor.presentation.nav_medicine.models.Orders

class OrderMedicineRvAdapter(val listener : OnItemTouchClickListener):
    RecyclerView.Adapter<OrderMedicineRvAdapter.OrderMedicineVH>() {

    inner class OrderMedicineVH(private val itemRvOrdersBinding : ItemRvOrdersBinding):
        RecyclerView.ViewHolder(itemRvOrdersBinding.root) {

        init {
            itemView.setOnClickListener {
                listener.onItem(differConfig.currentList[absoluteAdapterPosition])
            }
        }

        fun onBind(orders : Orders) {
            itemRvOrdersBinding.apply {
                imgOrderMedicineImage.setImageResource(orders.medicines.imageUrl)
                tvOrderMedicineName.text = orders.medicines.name
                tvOrderMedicinesClientName.text = orders.patient.name
                tvOrderQuantity.text = "Soni:${orders.quantity} dona"
                tvOrderMedicinePrise.text = "Narxi:${orders.medicines.price} so'm"
                tvOrderMedicineTotal.text = "Umumiy summa:${(orders.quantity * orders.medicines.price)} so'm"
            }
        }
    }

    fun mySubmitList(ordersList : ArrayList<Orders>) {
        differConfig.currentList.clear()
        differConfig.submitList(ordersList)
    }

    private var diffCallBack = object:DiffUtil.ItemCallback<Orders>() {
        override fun areItemsTheSame(oldItem : Orders , newItem : Orders) : Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem : Orders , newItem : Orders) : Boolean {
            return oldItem == newItem
        }
    }

    private val differConfig = AsyncListDiffer(this , diffCallBack)

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : OrderMedicineVH {
        return OrderMedicineVH(
            ItemRvOrdersBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )
    }

    override fun onBindViewHolder(holder : OrderMedicineVH , position : Int) {
        holder.onBind(differConfig.currentList[position])
    }

    override fun getItemCount() : Int {
        return differConfig.currentList.size
    }

    interface OnItemTouchClickListener {
        fun onItem(orders : Orders)
    }

}