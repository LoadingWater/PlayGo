package com.wspateam.playgo.fragments.controllers

import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.firebase.database.FirebaseDatabase
import com.wspateam.playgo.R
import com.wspateam.playgo.androidutilities.AndroidUtilities
import com.wspateam.playgo.fragments.LoginFragment
import com.wspateam.playgo.viewmodels.SharedApplicationViewModel

class LoginController(val loginFragment: LoginFragment)
{
    private val TAG = LoginController::class.java.simpleName
    private val sharedViewModel = ViewModelProvider(loginFragment).get(SharedApplicationViewModel::class.java)
    private val dbRef = sharedViewModel.firebaseDatabaseInstance.value?.reference ?: FirebaseDatabase.getInstance().reference
    private val view = loginFragment.requireView()
    private val activity = loginFragment.requireActivity()

    fun setUpViewsForHidingKeyboard()
    {
        view.findViewById<ConstraintLayout>(R.id.rootLoginFragment).setOnClickListener{
            AndroidUtilities.AndroidUtilities.hideKeyboard(view)
        }
        view.findViewById<TextView>(R.id.playGoLoginFragment).setOnClickListener {
            AndroidUtilities.AndroidUtilities.hideKeyboard(view)
        }
        view.findViewById<TextView>(R.id.loginLabelLoginFragment).setOnClickListener {
            AndroidUtilities.AndroidUtilities.hideKeyboard(view)
        }
        view.findViewById<ConstraintLayout>(R.id.frameLoginFragment).setOnClickListener {
            AndroidUtilities.AndroidUtilities.hideKeyboard(view)
        }
    }

    fun loginUser()
    {
        val email = view.findViewById<EditText>(R.id.emailFieldLoginFragment).text.toString().trim()
        val password = view.findViewById<EditText>(R.id.passwordFieldLoginFragment).text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty())
        {
            sharedViewModel.firebaseAuthInstance.value?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener {
                if (it.isSuccessful)
                {
                    Toast.makeText(loginFragment.context, "Logged in.", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_roomsFragment)
                }
                else
                {
                    Toast.makeText(loginFragment.context, "Failed to login.\n${it.exception?.message.toString()}", Toast.LENGTH_LONG).show()
                    Log.e(TAG, it.exception?.message.toString())
                }
            }
        }
        else
        {
            Toast.makeText(loginFragment.context, "Empty fields.", Toast.LENGTH_SHORT).show()
        }
    }

}