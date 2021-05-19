package com.Fili.vamz_diar

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthentificationModel: ViewModel() {

    private var FirebaseAuthInstance = FirebaseAuth.getInstance()

    fun LogInUser(userEmail: String, userPassword: String) {
        FirebaseAuthInstance.signInWithEmailAndPassword(userEmail,userPassword).addOnSuccessListener {

        }
    }

    fun registerNewUser(userEmail: String, userPassword: String) {
        FirebaseAuthInstance.createUserWithEmailAndPassword(userEmail,userPassword).addOnSuccessListener {

        }
    }
}