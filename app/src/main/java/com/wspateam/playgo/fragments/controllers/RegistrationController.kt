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
import com.wspateam.playgo.fragments.RegistrationFragment
import com.wspateam.playgo.models.User
import com.wspateam.playgo.viewmodels.SharedApplicationViewModel


class RegistrationController(val registrationFragment: RegistrationFragment)
{
    private val TAG = "RegistrationController"
    private val sharedViewModel = ViewModelProvider(registrationFragment).get(SharedApplicationViewModel::class.java)
    private val view = registrationFragment.requireView()
    private val activity = registrationFragment.requireActivity()

    fun setUpViewsForHidingKeyboard()
    {
        view.findViewById<ConstraintLayout>(R.id.rootRegistrationFragment).setOnClickListener{
            AndroidUtilities.AndroidUtilities.hideKeyboard(view)
        }
        view.findViewById<TextView>(R.id.playGoRegistrationFragment).setOnClickListener {
            AndroidUtilities.AndroidUtilities.hideKeyboard(view)
        }
        view.findViewById<ConstraintLayout>(R.id.frameRegistrationFragment).setOnClickListener {
            AndroidUtilities.AndroidUtilities.hideKeyboard(view)
        }
        view.findViewById<TextView>(R.id.registerRegistrationFragment).setOnClickListener {
            AndroidUtilities.AndroidUtilities.hideKeyboard(view)
        }
    }

    fun registerUser()
    {
        val username = view.findViewById<EditText>(R.id.usernameFieldRegistrationFragment).text.toString().trim()
        val email = view.findViewById<EditText>(R.id.emailFieldRegistrationFragment).text.toString().trim()
        val password = view.findViewById<EditText>(R.id.passwordFieldRegistrationFragment).text.toString().trim()

        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty())
        {
            val a = sharedViewModel.firebaseAuthInstance.value?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener {
                if (it.isSuccessful)
                {
                    val uid = it.result?.user?.uid
                    pushUserToFirebase(uid!!, username, email, password)
                    Toast.makeText(registrationFragment.context, "User registered.", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_roomsFragment)
                }
                else
                {
                    Toast.makeText(registrationFragment.context, "Registration failed.\n${it.exception?.message.toString()}", Toast.LENGTH_LONG).show()
                    Log.e(TAG, it.exception?.message.toString())
                }
            }
        }
        else
        {
            Toast.makeText(registrationFragment.context, "Empty fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun pushUserToFirebase(uid: String, username: String, email: String, password: String)
    {
        val ref = sharedViewModel.firebaseDatabaseInstance.value?.reference ?: FirebaseDatabase.getInstance().reference
        val user = User(uid, username, email, password)
        ref.child("users").child(uid).setValue(user).addOnCompleteListener {
            if (it.isSuccessful)
            {
                Log.i(TAG, "User pushed to the db.")
            }
            else
            {
                Log.e(TAG, "Didn't push a user to the db.")
            }
        }
    }
}