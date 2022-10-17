package com.github.abraga.fuzetest.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.abraga.fuzetest.databinding.ActivityMainBinding
import com.github.abraga.fuzetest.presentation.main.adapter.MatchesAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val matchesAdapter = MatchesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}