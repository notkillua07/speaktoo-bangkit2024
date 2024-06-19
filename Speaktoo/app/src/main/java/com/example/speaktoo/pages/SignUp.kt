package com.example.speaktoo.pages

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.speaktoo.databinding.SignupBinding
import android.widget.Toast
import com.example.speaktoo.api.base.RetrofitClient
import com.example.speaktoo.models.SignupResponse
import com.example.speaktoo.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp : AppCompatActivity() {
    private lateinit var binding: SignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.arrowSignUp.setOnClickListener {
            // Close the current activity
            finish()
        }

        binding.intermediateSignUp.setOnClickListener {
            val email = binding.editEmailSignUp.text.toString()
            val password = binding.editPasswordSignUp.text.toString()
            val username = binding.editUsername.text.toString()

            val user = User(email, password, username)

            RetrofitClient.instance.signup(user).enqueue(object : Callback<SignupResponse> {
                override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                    if (response.isSuccessful) {
                        val uid = response.body()?.data?.uid
                        if (uid != null) {
                            val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
                            with(sharedPreferences.edit()) {
                                putString("uid", uid)
                                apply()
                            }
                            Toast.makeText(this@SignUp, "Signup Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@SignUp, Proficiency::class.java)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this@SignUp, "Signup Failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Toast.makeText(this@SignUp, "Signup Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }




}
