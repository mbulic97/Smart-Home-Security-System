package com.example.smarthomesecuritysystemmirnes.viewmodel

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.ViewModel
import com.example.smarthomesecuritysystemmirnes.model.SensorModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _hasInternet = MutableStateFlow(false)
    val hasInternet: StateFlow<Boolean> = _hasInternet

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _sensor = MutableStateFlow(SensorModel())
    val sensor: StateFlow<SensorModel> = _sensor

    fun observeInternet(connectivityManager: ConnectivityManager) {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _hasInternet.value = true
            }

            override fun onLost(network: Network) {
                _hasInternet.value = false
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)
    }

    fun loadUserName() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        Firebase.firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                _userName.value = document.getString("name") ?: ""
                val arduinoId = document.getString("arduinoId")
                if (!arduinoId.isNullOrEmpty()) {
                    observeSensors(arduinoId)
                }
            }
    }

    private fun observeSensors(arduinoId: String) {
        val database = FirebaseDatabase.getInstance().getReference(arduinoId)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    _sensor.value = SensorModel(
                        arduinoId = arduinoId,
                        alarm = snapshot.child("Alarm").getValue(Boolean::class.java) ?: false,
                        humidity = snapshot.child("Humidity").getValue(Int::class.java) ?: 0,
                        pir = snapshot.child("PIR").getValue(Boolean::class.java) ?: false,
                        waterLevel = snapshot.child("Water_Level").getValue(Boolean::class.java) ?: false,
                        door = snapshot.child("door").getValue(String::class.java) ?: "",
                        temp = snapshot.child("temp").getValue(Int::class.java) ?: 0
                    )
                }
                else{
                    _sensor.value = _sensor.value.copy(
                        arduinoId = "Device with ID $arduinoId was not found in the database."
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _sensor.value = _sensor.value.copy(
                    arduinoId = "Database error: ${error.message}"
                )
            }
        })
    }
}
