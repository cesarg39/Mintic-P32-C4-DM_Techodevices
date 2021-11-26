package com.misiontic2022.technodevices.view.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.misiontic2022.technodevices.R
import com.misiontic2022.technodevices.core.hide
import com.misiontic2022.technodevices.core.show
import com.misiontic2022.technodevices.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragContent) as NavHostFragment
        navController = navHostFragment.navController
        binding.bnvMenu.setupWithNavController(navController)
        observeDestinationChange()
    }

    private fun observeDestinationChange(){
        navController.addOnDestinationChangedListener{ navController: NavController, navDestination: NavDestination, bundle: Bundle? ->
            when (navDestination.id){
                R.id.signUpFragment2-> {
                    binding.bnvMenu.hide()
                }
                R.id.loginFragment->{
                    binding.bnvMenu.hide()
                }
                else ->{
                    binding.bnvMenu.show()
                }
            }
        }
    }

}