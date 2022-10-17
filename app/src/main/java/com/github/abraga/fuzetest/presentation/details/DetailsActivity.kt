package com.github.abraga.fuzetest.presentation.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.abraga.fuzetest.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}