package com.example.onlineveterinarydoctor.presentation.nav_patient.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlineveterinarydoctor.R
import com.example.onlineveterinarydoctor.databinding.ScreenMessagesPatientBinding
import com.example.onlineveterinarydoctor.presentation.nav_patient.adapter.DoctorUserRvMessageAdapter
import com.example.onlineveterinarydoctor.utils.scope

class MessagesPatientScreen:Fragment(R.layout.screen_messages_patient) {

    private val binding by viewBinding(ScreenMessagesPatientBinding::bind)
    private lateinit var doctorUserRvMessageAdapter : DoctorUserRvMessageAdapter
    private lateinit var allMessageList : ArrayList<String>

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)

        activity?.window?.setBackgroundDrawableResource(R.drawable.background_message1)

        allMessageList = ArrayList()
        allMessageList.add("Hello1")
        allMessageList.add("Hello2")
        doctorUserRvMessageAdapter = DoctorUserRvMessageAdapter()
        doctorUserRvMessageAdapter.mySubmitList(allMessageList)
        rvMessage.apply {
            setHasFixedSize(true)
            adapter = doctorUserRvMessageAdapter
        }

        imgSend.setOnClickListener {
            val message = etMessage.text.toString()
            if (message.isNotEmpty()) {
                allMessageList.add(message)
                doctorUserRvMessageAdapter.mySubmitList(allMessageList)
                doctorUserRvMessageAdapter.notifyDataSetChanged()
                rvMessage.scrollToPosition(doctorUserRvMessageAdapter.itemCount - 1)
                etMessage.text.clear()
            } else {
                Toast.makeText(requireContext() , "Empty" , Toast.LENGTH_SHORT).show()
            }
        }

    }

}