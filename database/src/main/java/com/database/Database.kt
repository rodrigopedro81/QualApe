package com.database

import android.util.Log
import com.domain.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object Database {

    fun saveUserData(user: User, onComplete: () -> Unit) {
        getFirestoreInstance().collection("Users").add(user).addOnCompleteListener {
            onComplete.invoke()
            if (it.isSuccessful) Log.d("Tag", "E conseguiu!")
        }
    }

    fun getFirestoreInstance() = Firebase.firestore
}