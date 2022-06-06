package com.example.infograce

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.infograce.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MainActivityBinding.inflate(layoutInflater).root)
    }

}

