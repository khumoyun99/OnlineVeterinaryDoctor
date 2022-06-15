package com.example.onlineveterinarydoctor.presentation.nav_patient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineveterinarydoctor.databinding.ItemRvAnimalsBinding
import com.example.onlineveterinarydoctor.presentation.nav_patient.models.Animal

class AnimalsRvAdapter(val listener:OnAnimalsClickListener):RecyclerView.Adapter<AnimalsRvAdapter.MyAnimalsVH>() {

    inner class MyAnimalsVH(private val itemRvAnimalsBinding : ItemRvAnimalsBinding):
        RecyclerView.ViewHolder(itemRvAnimalsBinding.root) {

        init {
            itemView.setOnClickListener {
                listener.onItemClick(differList.currentList[absoluteAdapterPosition])
            }
        }

        fun onBind(animal : Animal) {
            itemRvAnimalsBinding.apply {
                imgAnimalPhoto.setImageResource(animal.image)
                tvAnimalsName.text = animal.name
            }
        }
    }

    fun mySubmitList(animalList : ArrayList<Animal>) {
        differList.currentList.clear()
        differList.submitList(animalList)
    }

    private val difCallBack = object:DiffUtil.ItemCallback<Animal>() {
        override fun areItemsTheSame(oldItem : Animal , newItem : Animal) : Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem : Animal , newItem : Animal) : Boolean {
            return oldItem == newItem
        }
    }

    private var differList = AsyncListDiffer(this , difCallBack)


    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : MyAnimalsVH {
        return MyAnimalsVH(
            ItemRvAnimalsBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )
    }

    override fun onBindViewHolder(holder : MyAnimalsVH , position : Int) {
        holder.onBind(differList.currentList[position])
    }

    override fun getItemCount() : Int {
        return differList.currentList.size
    }

    interface OnAnimalsClickListener {
        fun onItemClick(animal : Animal)
    }


}