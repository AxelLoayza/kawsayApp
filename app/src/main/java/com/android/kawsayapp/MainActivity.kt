package com.android.kawsayapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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

        // 1. Configurar la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // 3. Modificamos el listener para que YA NO cambie el título del header
        bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment? = when (item.itemId) {
                R.id.nav_home -> homeFragment
                R.id.nav_chatbot -> chatbotFragment
                R.id.nav_groups -> groupsFragment
                R.id.nav_schedule -> scheduleFragment
                R.id.nav_profile -> profileFragment
                else -> null
            }
            if (selectedFragment != null) {
                replaceFragment(selectedFragment)
            }
            true
        }

        // Establecer el fragmento inicial
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.nav_home
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    // 4. Inflar el menú con nuestro icono de notificaciones
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    // 5. Manejar los clics en los items del menú (el icono de notificaciones)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_notifications -> {
                // Aquí iría la lógica para abrir la pantalla de notificaciones
                Toast.makeText(this, "Notificaciones clickeadas", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}