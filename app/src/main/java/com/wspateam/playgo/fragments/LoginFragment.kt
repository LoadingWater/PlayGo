package com.wspateam.playgo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.wspateam.playgo.R
import com.wspateam.playgo.fragments.controllers.LoginController

class LoginFragment : Fragment()
{
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_login, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)

		val loginController = LoginController(this)
		loginController.setUpViewsForHidingKeyboard()

		view.findViewById<TextView>(R.id.createLoginFragment).setOnClickListener {
			Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrationFragment)
		}

		val loginButton = view.findViewById<Button>(R.id.loginButtonLoginFragment)
		loginButton.setOnClickListener {
			loginController.loginUser()
		}

	}
}