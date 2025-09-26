package com.example.smarthomesecuritysystemmirnes.viewmodel

import androidx.lifecycle.ViewModel
import com.example.smarthomesecuritysystemmirnes.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore

class AuthViewModel : ViewModel(){
    private val auth = Firebase.auth
    private val firestore = Firebase.firestore
    private val database = FirebaseDatabase.getInstance()

    fun login(email: String,password: String,arduinoId: String,  onResult: (Boolean,String?)-> Unit){

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    database.getReference(arduinoId)
                        .addListenerForSingleValueEvent(object : ValueEventListener{

                            override fun onDataChange(snapshot: DataSnapshot) {

                                if(snapshot.exists()){
                                    onResult(true,null)
                                }else{
                                    auth.signOut()
                                    onResult(false,"Invalid Arduino ID")
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {
                                onResult(false,"Database error: ${error.message}")
                            }
                        }
                        )

                }
                else{
                    onResult(false,"Something went wrong")
                }
            }

    }
    fun signup(email: String, name: String, password: String, arduinoId: String, onResult: (Boolean,String?)-> Unit){
        database.getReference(arduinoId)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        auth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener {
                                if(it.isSuccessful){
                                    val userId = it.result?.user?.uid
                                    val userModel = UserModel(email,name,arduinoId,userId!!)
                                    firestore.collection("users").document(userId)
                                        .set(userModel)
                                        .addOnCompleteListener { dbTask->
                                            if(dbTask.isSuccessful){
                                                onResult(true,null)
                                            }else{
                                                onResult(false,"Something went wrong")
                                            }
                                        }
                                }
                                else{
                                    onResult(false,it.exception?.localizedMessage)
                                }
                            }
                    }
                    else{
                        onResult(false, "Invalid Arduino ID")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onResult(false,"Database error: ${error.message}")
                }
            })


    }

}