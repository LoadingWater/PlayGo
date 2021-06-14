package com.wspateam.playgo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.wspateam.playgo.models.User

class SharedApplicationViewModel: ViewModel()
{
    val firebaseAuthInstance = MutableLiveData<FirebaseAuth>(FirebaseAuth.getInstance())
    val firebaseDatabaseInstance = MutableLiveData<FirebaseDatabase>(FirebaseDatabase.getInstance())

    val currentUser = MutableLiveData<User>()
}