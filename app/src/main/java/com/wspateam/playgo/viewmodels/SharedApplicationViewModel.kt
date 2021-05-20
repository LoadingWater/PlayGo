package com.wspateam.playgo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SharedApplicationViewModel: ViewModel()
{
    val firebaseAuthInstance = MutableLiveData<FirebaseAuth>(FirebaseAuth.getInstance())
}