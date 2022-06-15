package com.example.onlineveterinarydoctor.presentation.nav_patient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineveterinarydoctor.databinding.ItemRvPatientBinding
import com.example.onlineveterinarydoctor.presentation.nav_patient.models.Patient

class PatientRvAdapter(var listener : OnPatientItemClickListener):
    RecyclerView.Adapter<PatientRvAdapter.MyPatientVH>() {


    inner class MyPatientVH(private var itemRvPatientBinding : ItemRvPatientBinding):
        RecyclerView.ViewHolder(itemRvPatientBinding.root) {

        init {
            itemRvPatientBinding.imgSendImageIcon.setOnClickListener {
                listener.onMessageClick(differConfig.currentList[absoluteAdapterPosition])
            }

            itemRvPatientBinding.imgAnimalImageIcon.setOnClickListener {
                listener.onPatClick(differConfig.currentList[absoluteAdapterPosition])
            }
        }

        fun onBind(patient : Patient) {
            itemRvPatientBinding.apply {
                imgClientPhoto.setImageResource(patient.img)
                tvClientName.text = patient.name
                tvClientAddress.text = patient.address
                tvClientPhoneNumber.text = patient.phoneNumber
            }
        }
    }


    fun mySubmitList(patientList : ArrayList<Patient>) {
        differConfig.currentList.clear()
        differConfig.submitList(patientList)
    }

    private val difCallBack = object:DiffUtil.ItemCallback<Patient>() {
        override fun areItemsTheSame(oldItem : Patient , newItem : Patient) : Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem : Patient , newItem : Patient) : Boolean {
            return oldItem == newItem
        }
    }

    private val differConfig = AsyncListDiffer(this , difCallBack)

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : MyPatientVH {
        return MyPatientVH(
            ItemRvPatientBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )
    }

    override fun onBindViewHolder(holder : MyPatientVH , position : Int) {
        return holder.onBind(differConfig.currentList[position])
    }

    override fun getItemCount() : Int {
        return differConfig.currentList.size
    }

    interface OnPatientItemClickListener {
        fun onPatClick(patient : Patient)
        fun onMessageClick(patient : Patient)
    }

}