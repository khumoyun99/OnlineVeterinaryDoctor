package com.example.onlineveterinarydoctor.presentation.nav_profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlineveterinarydoctor.R
import com.example.onlineveterinarydoctor.databinding.PageProfileBinding
import com.example.onlineveterinarydoctor.utils.scope

class ProfilePage:Fragment(R.layout.page_profile) {

    private val binding by viewBinding(PageProfileBinding::bind)

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)


    }

}