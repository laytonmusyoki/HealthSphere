package com.example.healthsphere

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.healthsphere.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var fragmentmanager : FragmentManager
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()

        setContentView(binding.root)

        // Apply window insets to toolbar to ensure it doesn't overlap with the system UI
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(view.paddingLeft, systemBarsInsets.top, view.paddingRight, view.paddingBottom)
            insets
        }

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(this,binding.drawerLayout, binding.toolbar, R.string.nav_open, R.string.nav_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationDrawer.setNavigationItemSelectedListener(this)
        binding.BottomNavigation.background = null

        // Set default selected item as Home
        binding.BottomNavigation.selectedItemId = R.id.bottom_home

        binding.BottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.bottom_home -> openFragment(HomeFragment())
                R.id.feed -> openFragment(FeedFragment())
                R.id.Community ->openFragment(CommunityFragment())
            }
            true
        }

        // Ensure no item is selected initially
        fragmentmanager = supportFragmentManager
        openFragment(HomeFragment())
    }

//    private fun clearBottomNavigationSelection(bottomNavigationView: BottomNavigationView) {
//        val menu = bottomNavigationView.menu
//        for (i in 0 until menu.size()) {
//            menu.getItem(i).isChecked = false
//        }
//    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_prime -> openFragment(PrimeFragment())
            R.id.nav_more -> openFragment(MoreFragment())
            R.id.nav_contact -> openFragment(ContactFragment())
            R.id.nav_logout -> openFragment(LogoutFragment())
            R.id.nav_home -> Toast.makeText(this,"Home", Toast.LENGTH_SHORT).show()
            R.id.nav_health -> Toast.makeText(this,"Your Health Priority",Toast.LENGTH_SHORT).show()
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }

    }
    private fun openFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = fragmentmanager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}
