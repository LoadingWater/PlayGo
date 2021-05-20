package com.wspateam.playgo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.wspateam.playgo.R
import com.wspateam.playgo.fragments.controllers.RegistrationController

class RegistrationFragment : Fragment()
{
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View?
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_registration, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val registrationController = RegistrationController(this)
		registrationController.setUpViewsForHidingKeyboard()

		val registerButton = view.findViewById<Button>(R.id.registerButtonRegistrationFragment)
		registerButton.setOnClickListener {
			registrationController.registerUser()
		}

	}
}