package com.awilab.moviedb.ui.media.xml

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.awilab.moviedb.databinding.ActivityXmlMediaBinding

class XmlMediaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityXmlMediaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityXmlMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
    }
}