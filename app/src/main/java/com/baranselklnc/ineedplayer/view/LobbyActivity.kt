package com.baranselklnc.ineedplayer.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.baranselklnc.ineedplayer.R
import com.baranselklnc.ineedplayer.databinding.ActivityLobbyBinding
import com.baranselklnc.ineedplayer.databinding.ActivityLoginBinding

class LobbyActivity : AppCompatActivity() {
    lateinit var binding: ActivityLobbyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLobbyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}