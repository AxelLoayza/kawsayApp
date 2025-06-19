package com.android.kawsayapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val chatbotFragment = ChatbotFragment()
    private val groupsFragment = GroupsFragment()
    private val scheduleFragment = ScheduleFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> replaceFragment(homeFragment)
                R.id.nav_chatbot -> replaceFragment(chatbotFragment)
                R.id.nav_groups -> replaceFragment(groupsFragment)
                R.id.nav_schedule -> replaceFragment(scheduleFragment)
                R.id.nav_profile -> replaceFragment(profileFragment)
            }
            true
        }

        // Establecer la pestaña de inicio por defecto
        bottomNavigationView.selectedItemId = R.id.nav_home
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}