package com.baranselklnc.ineedplayer.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.baranselklnc.ineedplayer.databinding.ActivityProfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfilActivity : AppCompatActivity() {


    lateinit var binding: ActivityProfilBinding
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance() //localized
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")


        var currentUser = auth.currentUser
        //binding.profilePositionText.text="About: "+currentUser?.


        binding.ProfileMailText.text = "Your email: " + currentUser?.email
        //realtimedatabase 'den idden altındaki childlerin içindeki veriyi sayfaya aktarılacak
        var userReference = databaseReference?.child(currentUser?.uid!!)
        userReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.profileFullNameText.text =
                    "Full Name: " + snapshot.child("adisoyadi").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        userReference?.addValueEventListener(object : ValueEventListener { //sonradan
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.profilePositionText.text =
                    "About: " + snapshot.child("about").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        //Çıkış yap
        binding.profileExitButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@ProfilActivity, LoginActivity::class.java))
            finish()
        }
        //Hesap sil
        binding.profileDeleteButton.setOnClickListener {
            currentUser?.delete()?.addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Your account has been deleted",
                        Toast.LENGTH_LONG
                    ).show()
                    auth.signOut()
                    startActivity(Intent(this@ProfilActivity, LoginActivity::class.java))
                    finish()
                    var currentUserDb =
                        currentUser?.let { it1 -> databaseReference?.child(it1.uid) }
                    currentUserDb?.removeValue()
                    Toast.makeText(
                        applicationContext,
                        "We deleted your all data. Don't leave us alone.",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }

        }
        //güncelle butonu
        binding.profileUpdateButton.setOnClickListener {

            startActivity(Intent(this@ProfilActivity, UpdateActivity::class.java))
            finish()
        }
        binding.profilelobby.setOnClickListener {

            startActivity(Intent(this@ProfilActivity, LobbyActivity::class.java))
            finish()

        }
    }
}