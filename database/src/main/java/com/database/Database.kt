package com.database

import com.database.Constants.TEST_USER_COLLECTION
import com.database.Constants.USER_COLLECTION
import com.domain.model.UserInfo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object Database {

    fun saveUserData(
        userInfo: UserInfo,
        onComplete: (wasSuccessful: Boolean) -> Unit
    ) {
        getFirestoreInstance()
            .collection(getUserCollection())
            .document(userInfo.email).set(userInfo).addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
    }

    private fun getUserCollection(): String =
        if (BuildConfig.DEBUG) TEST_USER_COLLECTION else USER_COLLECTION

    fun fetchUserDataForSingleton(userEmail: String) {
        getFirestoreInstance().collection(getUserCollection()).document(userEmail).get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val userInfo = it.result.toObject(UserInfo::class.java)
                    LoggedUser.info = userInfo ?: return@addOnCompleteListener
                }
            }
    }

    fun getFirestoreInstance() = Firebase.firestore
}