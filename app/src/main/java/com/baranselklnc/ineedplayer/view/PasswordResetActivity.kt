package com.baranselklnc.ineedplayer.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.baranselklnc.ineedplayer.databinding.ActivityPasswordResetBinding
import com.google.firebase.auth.FirebaseAuth

class PasswordResetActivity : AppCompatActivity() {
    lateinit var binding: ActivityPasswordResetBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityPasswordResetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()
        binding.resetbutton.setOnClickListener {
            var presetemail=binding.resetemail.text.toString().trim()
            if(TextUtils.isEmpty(presetemail)){
                binding.resetemail.error="Please write your e-mail address"
            }
            else {
                auth.sendPasswordResetEmail(presetemail).addOnCompleteListener(this){sifirlama->
                    if(sifirlama.isSuccessful){
                        binding.passwordresetmessage.text="We have sent a link to your e-mail address.Please check"
                    }
                    else{
                       binding.passwordresetmessage.text="Upps! Reset failed"
                    }
                }
            }
        }
        //go to login page
        binding.resetloginbutton.setOnClickListener {
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}