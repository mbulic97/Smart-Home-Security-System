package com.example.smarthomesecuritysystemmirnes.viewmodel

import androidx.lifecycle.ViewModel
import com.example.smarthomesecuritysystemmirnes.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel(){
    private val _userModel = MutableStateFlow(UserModel())
    val userModel: StateFlow<UserModel> = _userModel

    init {
        loadUser()
    }

    private fun loadUser() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        Firebase.firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                val result = document.toObject(UserModel::class.java)
                if (result != null) {
                    _userModel.value = result
                }
            }
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
}