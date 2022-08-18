package com.baranselklnc.ineedplayer.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.baranselklnc.ineedplayer.databinding.ActivityUpdateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UpdateActivity : AppCompatActivity() {
    lateinit var binding:ActivityUpdateBinding
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
    
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding=ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance() //localized
        database=FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("profile")

        var currentUser=auth.currentUser
        binding.updateemail.setText(currentUser?.email)
        //rtdb'den id den ad soyad
        var userReference=databaseReference?.child(currentUser?.uid!!)
        userReference?.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.updatenamesurname.setText(snapshot.child("adisoyadi").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        // activeupdatebutton
        binding.updateactivebutton.setOnClickListener {
            var updateemail=binding.updateemail.text.toString().trim()
            currentUser!!.updateEmail(updateemail).addOnCompleteListener{
                task->
                if(task.isSuccessful){
                    Toast.makeText(applicationContext,"Your e-mail account has been updated",Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(applicationContext,"Upss! E mail update operation failed,try again",Toast.LENGTH_SHORT).show()

                }
            }
            //paswordUpdate
          //  var updatepassword=binding.updatepassword.text.toString().trim()
         /*   currentUser!!.updatePassword(updatepassword).addOnCompleteListener{
                    task->
                if(task.isSuccessful ){
                    Toast.makeText(applicationContext,"Your password has been updated",Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(applicationContext,"Upss!Password update operation failed,try again",Toast.LENGTH_SHORT).show()

                }
            }*/


            //name-surname update
            var currentUserDb=currentUser?.let{it1->databaseReference?.child(it1.uid )}
            currentUserDb?.removeValue()
            currentUserDb?.child("adisoyadi")?.setValue(binding.updatenamesurname.text.toString())
            Toast.makeText(applicationContext,"Your new name suits you very well",Toast.LENGTH_SHORT).show()

            databaseReference=database?.reference!!.child("about") //sonradan eklendi
            currentUserDb?.child("about")?.setValue(binding.updateabout.text.toString())//sonradan eklendi
        }
        binding.updateloginbutton.setOnClickListener{
            intent= Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}