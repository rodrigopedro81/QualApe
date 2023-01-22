package com.database

import com.database.Constants.ATTRIBUTE_FOOD_ID_LIST
import com.database.Constants.FOOD_COLLECTION
import com.database.Constants.TEST_FOOD_COLLECTION
import com.database.Constants.TEST_USER_COLLECTION
import com.database.Constants.USER_COLLECTION
import com.domain.model.Food
import com.domain.model.UserInfo
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object Database {

    private fun getFirestoreInstance() = Firebase.firestore

    fun saveUserData(
        userInfo: UserInfo,
        onComplete: (wasSuccessful: Boolean) -> Unit
    ) {
        getUserCollection().document(userInfo.email).set(userInfo).addOnCompleteListener {
            onComplete.invoke(it.isSuccessful)
        }
    }

    fun fetchUserDataForSingleton(
        userEmail: String,
        onComplete: (wasSuccessful: Boolean) -> Unit
    ) {
        getUserCollection().document(userEmail).get().addOnCompleteListener {
            onComplete.invoke(it.isSuccessful)
            if (it.isSuccessful) {
                val userInfo = it.result.toObject(UserInfo::class.java)
                LoggedUser.info = userInfo ?: return@addOnCompleteListener
            }
        }
    }

    fun saveFood(
        newFood: Food,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        val newFoodDocumentReference = getFoodCollection().document()
        val userDocumentReference = getUserCollection().document(LoggedUser.info.email)
        val newFoodId = newFoodDocumentReference.id
        val newUserFoodIdList = LoggedUser.info.foodIdList + newFoodId
        newFoodDocumentReference.set(newFood).addOnSuccessListener {
            userDocumentReference.update(ATTRIBUTE_FOOD_ID_LIST, newUserFoodIdList)
                .addOnSuccessListener {
                    LoggedUser.info.foodIdList = newUserFoodIdList
                    onSuccess.invoke()
                }
                .addOnFailureListener {
                    onFailure.invoke()
                }
        }.addOnFailureListener {
            onFailure.invoke()
        }
    }


    private fun getUserCollection(): CollectionReference = getFirestoreInstance().collection(
        if (BuildConfig.DEBUG) TEST_USER_COLLECTION else USER_COLLECTION
    )

    private fun getFoodCollection(): CollectionReference = getFirestoreInstance().collection(
        if (BuildConfig.DEBUG) TEST_FOOD_COLLECTION else FOOD_COLLECTION
    )
}