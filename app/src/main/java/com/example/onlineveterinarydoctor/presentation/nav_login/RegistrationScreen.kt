package com.example.onlineveterinarydoctor.presentation.nav_login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlineveterinarydoctor.R
import com.example.onlineveterinarydoctor.databinding.ScreenRegistrationBinding
import com.example.onlineveterinarydoctor.utils.scope

class RegistrationScreen:Fragment(R.layout.screen_registration) {

    private val binding by viewBinding(ScreenRegistrationBinding::bind)

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)
    }


}