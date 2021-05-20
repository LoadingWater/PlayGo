package com.wspateam.playgo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)

		val sharedApplicationViewModel = MutableLiveData<FirebaseApp>();

		setContentView(R.layout.activity_main)
	}
}