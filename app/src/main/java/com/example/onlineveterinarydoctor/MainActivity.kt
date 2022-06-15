package com.example.onlineveterinarydoctor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlineveterinarydoctor.databinding.ActivityMainBinding
import com.example.onlineveterinarydoctor.utils.BottomBackStackController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

class MainActivity:AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private lateinit var appBarConfiguration : AppBarConfiguration
    private var currentNavController : LiveData<NavController>? = null
    private lateinit var toolbar : Toolbar

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }

        binding.apply {
            val materialShapeDrawable = appbarLayout.background as MaterialShapeDrawable
            materialShapeDrawable.shapeAppearanceModel =
                materialShapeDrawable.shapeAppearanceModel.toBuilder().setBottomRightCorner(
                    CornerFamily.ROUNDED , 30F
                ).setBottomLeftCorner(
                    CornerFamily.ROUNDED , 30F
                ).build()
        }


    }

    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        bottomNavigationView.background = null

        val navGraphIds = listOf(
            R.navigation.nav_patient_graph ,
            R.navigation.nav_medicine_graph ,
            R.navigation.nav_profile_graph
        )

        val bottomBackStackController = BottomBackStackController()
        val controller = bottomBackStackController.setupWithNavController(
            bottomNavigationView = bottomNavigationView ,
            navGraphIds = navGraphIds ,
            fragmentManager = supportFragmentManager ,
            containerId = R.id.main_nav_host_fragment
        )

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.patientPage ,
                R.id.medicinePage ,
                R.id.profilePage
            )
        )

        controller.observe(this , Observer { navController ->
            setupActionBarWithNavController(navController , appBarConfiguration)
        })

        currentNavController = controller


    }

    override fun onSupportNavigateUp() : Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun onRestoreInstanceState(savedInstanceState : Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }


}