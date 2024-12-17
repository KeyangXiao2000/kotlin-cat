package com.example.tdcat.view

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.tdcat.R
import com.example.tdcat.catModel.CatResponse
import com.example.tdcat.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var imgCat: ImageView
    private lateinit var btnCat: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        mainViewModel = MainViewModel()
        subscribe()

        imgCat = findViewById(R.id.img_cat)
        btnCat = findViewById(R.id.btn_cat)

        btnCat.setOnClickListener {
            mainViewModel.getCatData()
        }
    }

    private fun subscribe() {
        mainViewModel.isLoading.observe(this) { isLoading ->
        }

        mainViewModel.isError.observe(this) { isError ->
        }

        mainViewModel.catData.observe(this) { catData ->
            // Display cat
            setResultImg(catData)
        }
    }

    private fun setResultImg(catData: List<CatResponse>) {
         if (catData[0].url != null) {catData[0].url.let { url ->
             Glide.with(applicationContext).load(url).into(imgCat)
             imgCat.visibility = View.VISIBLE
        }} else imgCat.visibility = View.GONE
    }
}