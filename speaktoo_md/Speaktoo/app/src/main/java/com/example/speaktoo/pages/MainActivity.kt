package com.example.speaktoo.pages

import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.speaktoo.api.base.RetrofitClient
import com.example.speaktoo.databinding.LoginBinding
import com.example.speaktoo.models.LoginResponse
import com.example.speaktoo.models.User
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: LoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        binding.signInLogin.setOnClickListener {
//            val intent = Intent(this, Proficiency::class.java)
//            startActivity(intent)
//        }

        binding.signInLogin.setOnClickListener {
            val email = binding.editEmailLogin.text.toString()
            val password = binding.editPasswordLogin.text.toString()

            val user = User(email, password)

            RetrofitClient.instance.login(user).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val uid = response.body()?.data?.uid
                        val email = response.body()?.data?.email
                        val username = response.body()?.data?.username

                        if (uid != null && email != null && username != null) {
                            val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
                            with(sharedPreferences.edit()) {
                                putString("uid", uid)
                                putString("email", email)
                                putString("username", username)
                                apply()
                            }
                            Toast.makeText(this@MainActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@MainActivity, Proficiency::class.java)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Login Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun goToForget(view: View) {
        // Handle the click event here
        val intent = Intent(this, Forgot::class.java)
        startActivity(intent)
    }

    fun goToSignUp(view: View) {
        // Handle the click event here
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
    }


}