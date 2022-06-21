package com.example.onlineveterinarydoctor.presentation.nav_patient.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineveterinarydoctor.databinding.ItemRvDoctorMessageBinding
import com.example.onlineveterinarydoctor.databinding.ItemRvUserMessageBinding
import com.example.onlineveterinarydoctor.presentation.nav_patient.models.Messages
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DoctorUserRvMessageAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val user = 0
    private val doctor = 1
    private var doctorsUid : String? = null
    private val simpleDateFormat = SimpleDateFormat("HH:mm")

    inner class MyUserMessageVH(private val itemRvUserMessageBinding : ItemRvUserMessageBinding):
        RecyclerView.ViewHolder(itemRvUserMessageBinding.root) {

        fun onBind(message : Messages) {
            itemRvUserMessageBinding.apply {
                tvUserItemMessage.text = message.message
                tvArriveMessageDate.text = simpleDateFormat.format(Date(message.date ?: 0))
            }
        }
    }

    inner class MyDoctorMessageVH(private val itemRvDoctorMessageBinding : ItemRvDoctorMessageBinding):
        RecyclerView.ViewHolder(itemRvDoctorMessageBinding.root) {

        fun onBind(message : Messages) {
            itemRvDoctorMessageBinding.apply {
                tvDoctorItemMessage.text = message.message
                tvSendMessageDate.text = simpleDateFormat.format(Date(message.date ?: 0))
            }
        }
    }

    val DIFF_CALLBACK = object:DiffUtil.ItemCallback<Messages>() {
        override fun areItemsTheSame(oldItem : Messages , newItem : Messages) : Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem : Messages , newItem : Messages) : Boolean {
            return oldItem == newItem
        }
    }

    private val allMessageList = AsyncListDiffer(this , DIFF_CALLBACK)

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : RecyclerView.ViewHolder {
        return when (viewType) {
            doctor -> {
                MyDoctorMessageVH(
                    ItemRvDoctorMessageBinding.inflate(
                        LayoutInflater.from(parent.context) ,
                        parent ,
                        false
                    )
                )
            }
            else -> {
                MyUserMessageVH(
                    ItemRvUserMessageBinding.inflate(
                        LayoutInflater.from(parent.context) ,
                        parent ,
                        false
                    )
                )
            }

        }

    }

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder , position : Int) {
        if (getItemViewType(position) == doctor) {
            (holder as MyDoctorMessageVH).onBind(allMessageList.currentList[position])
        } else {
            (holder as MyUserMessageVH).onBind(allMessageList.currentList[position])
        }
    }

    override fun getItemCount() : Int {
        return allMessageList.currentList.size
    }

    override fun getItemViewType(position : Int) : Int {
        return if (allMessageList.currentList[position].from == doctorsUid) {
            doctor
        } else {
            user
        }
    }

    fun mySubmitList(messageList : ArrayList<Messages> , doctorsUid : String?) {
        allMessageList.submitList(messageList)
        this.doctorsUid = doctorsUid
    }
}