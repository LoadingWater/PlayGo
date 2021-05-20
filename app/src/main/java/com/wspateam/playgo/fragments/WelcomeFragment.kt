package com.wspateam.playgo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.wspateam.playgo.R

class WelcomeFragment : Fragment()
{

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View?
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_welcome, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)

		val login = view.findViewById<Button>(R.id.loginWelcomeFragment)
		val register = view.findViewById<Button>(R.id.createWelcomeFragment)

		login.setOnClickListener {
			Navigation.findNavController(requireView()).navigate(R.id.action_welcomeFragment_to_loginFragment)
		}

		register.setOnClickListener {
			Navigation.findNavController(requireView()).navigate(R.id.action_welcomeFragment_to_registrationFragment)
		}
	}
}