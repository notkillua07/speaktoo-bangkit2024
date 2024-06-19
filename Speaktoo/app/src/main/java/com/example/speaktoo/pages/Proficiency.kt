package com.example.speaktoo.pages

// Proficiency.kt
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.speaktoo.R
import com.example.speaktoo.databinding.ProficiencyBinding
import com.google.android.material.navigation.NavigationView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class Proficiency : AppCompatActivity() {
    private lateinit var binding: ProficiencyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProficiencyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            // Close the sidebar when an item is clicked
            binding.drawerLayout.closeDrawer(GravityCompat.START)

            // Start the corresponding activity based on the clicked item
            when (menuItem.itemId) {
                R.id.nav_profile -> {
                    // Start Profile activity
                    startActivity(Intent(this, Profile::class.java))
                    true
                }
                R.id.nav_logout -> {
                    // Start Profile activity
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Retrieve user information from SharedPreferences
        val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "")
        val username = sharedPreferences.getString("username", "")
        val profilePic = sharedPreferences.getString("profile_pic", "")

        // Update sidebar header with user information
        val headerView = binding.navView.getHeaderView(0)
        val usernameTextView = headerView.findViewById<TextView>(R.id.header_username)
        val emailTextView = headerView.findViewById<TextView>(R.id.header_email)

        usernameTextView.text = username
        emailTextView.text = email

        binding.chatting.setOnClickListener {
            val intent = Intent(this, Level::class.java)
            intent.putExtra("TOPIC", "Chatting Day to Day")
            startActivity(intent)
        }

        binding.answering.setOnClickListener {
            val intent = Intent(this, Level::class.java)
            intent.putExtra("TOPIC", "Answering Interview")
            startActivity(intent)
        }

        binding.meeting.setOnClickListener {
            val intent = Intent(this, Level::class.java)
            intent.putExtra("TOPIC", "Meeting")
            startActivity(intent)
        }

        binding.expressing.setOnClickListener {
            val intent = Intent(this, Level::class.java)
            intent.putExtra("TOPIC", "Expressing Opinions")
            startActivity(intent)
        }

        binding.makingRequest.setOnClickListener {
            val intent = Intent(this, Level::class.java)
            intent.putExtra("TOPIC", "Making Request & Offering Help")
            startActivity(intent)
        }

        binding.givingReceiving.setOnClickListener {
            val intent = Intent(this, Level::class.java)
            intent.putExtra("TOPIC", "Giving & Receiving Direction")
            startActivity(intent)
        }

        binding.apologizing.setOnClickListener {
            val intent = Intent(this, Level::class.java)
            intent.putExtra("TOPIC", "Apologizing & Complaining")
            startActivity(intent)
        }


        binding.burgerProficiency.setOnClickListener {
            // Open or close the sidebar when the burger icon is clicked
            val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }
}
