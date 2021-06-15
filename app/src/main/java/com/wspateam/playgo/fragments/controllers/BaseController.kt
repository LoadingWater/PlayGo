package com.wspateam.playgo.fragments.controllers

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wspateam.playgo.viewmodels.SharedApplicationViewModel

open class BaseController(fragment: Fragment)
{
	protected val TAG = fragment::class.java.simpleName
	protected val sharedViewModel = ViewModelProvider(fragment).get(SharedApplicationViewModel::class.java)
	protected val dbRef = sharedViewModel.firebaseDatabaseInstance.value!!.reference
	protected val authIns = sharedViewModel.firebaseAuthInstance.value!!
	protected val activity = fragment.requireActivity()
	protected val view = fragment.requireView()
}