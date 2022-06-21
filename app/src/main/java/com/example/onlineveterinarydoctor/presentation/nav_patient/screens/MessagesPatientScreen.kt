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


    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)

    }

}