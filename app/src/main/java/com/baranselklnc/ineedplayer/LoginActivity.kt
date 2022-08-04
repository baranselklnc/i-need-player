package com.baranselklnc.ineedplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.baranselklnc.ineedplayer.databinding.ActivityLoginBinding
import com.baranselklnc.ineedplayer.databinding.ActivityUserBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance() //konum izni , erişim
        //oturum açıp açmadığının kontrolü
        var currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this@LoginActivity, ProfilActivity::class.java))
            finish()
        }
        binding.loginbutton.setOnClickListener {

            var loginemail = binding.loginemail.text.toString()
            var loginpassword = binding.loginpassword.text.toString()
            if (TextUtils.isEmpty(loginemail)) {
                binding.loginemail.error = "Please check your email account"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(loginpassword)) {
                binding.loginpassword.error = "Please check your password "
                return@setOnClickListener
            }

            //giriş bilgileri

            auth.signInWithEmailAndPassword(loginemail, loginpassword)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {

                        intent = Intent(applicationContext, ProfilActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Incorrect login please try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
        //new user go to
        binding.loginnewuser.setOnClickListener {
            intent = Intent(applicationContext, UserActivity::class.java)
            startActivity(intent)
            finish()
        }
            //reset password  go to
            binding.loginpasswordforgot.setOnClickListener {
                intent = Intent(applicationContext, PasswordResetActivity::class.java)
                startActivity(intent)
                finish()
            }



    }
}