package com.baranselklnc.ineedplayer

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.baranselklnc.ineedplayer.databinding.ActivityUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserActivity : AppCompatActivity() {

     lateinit var binding: ActivityUserBinding
     private lateinit var auth:FirebaseAuth
    var databaseReference:DatabaseReference?=null
    var database:FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance() //localized
        database=FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("profile")
        //Save me button with saving steps.

        binding.usersavebutton.setOnClickListener{

            var usernamesurname=binding.usernamesurname.text.toString().trim()
            var useremail=binding.useremail.text.toString().trim()
            var userpassword=binding.userpassword.text.toString().trim()
            var userabout=binding.userabout.text.toString().trim()


            if(TextUtils.isEmpty(usernamesurname))
            {
                binding.usernamesurname.error="Please enter your first and last name"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(useremail))
            {
                binding.useremail.error="Please enter your e-mail account."
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(userpassword))
            {
                binding.userpassword.error="Please enter your password."
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(userabout))
            {
                binding.userabout.error="Help us get to know you"
                return@setOnClickListener
            }

            // E- mail , password and username added to database
            auth.createUserWithEmailAndPassword(binding.useremail.text.toString(),binding.userpassword.text.toString())
                .addOnCompleteListener(this){ task->
                    if(task.isSuccessful){
                        //kullanıcı bilgileri alındı mı kontrol işlemi
                        var currentUser=auth.currentUser
                        //idye ad soyad kaydedildi.
                        var currentUserDb=currentUser?.let{it1->databaseReference?.child(it1.uid )}
                        currentUserDb?.child("adisoyadi")?.setValue(binding.usernamesurname.text.toString())
                        Toast.makeText(this@UserActivity,"Registration Successful",Toast.LENGTH_LONG).show()

                    }
                    else{
                        Toast.makeText(this@UserActivity,"Registration Failed",Toast.LENGTH_LONG).show()
                    }

                }
        }
        //go to login page
        binding.userloginbutton.setOnClickListener{
            intent= Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}