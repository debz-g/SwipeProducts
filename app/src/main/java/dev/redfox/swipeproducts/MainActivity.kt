package dev.redfox.swipeproducts

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dev.redfox.swipeproducts.databinding.ActivityMainBinding
import dev.redfox.swipeproducts.ui.SearchProductFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}