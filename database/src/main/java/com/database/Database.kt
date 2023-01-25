package com.database

import com.database.Constants.ATTRIBUTE_FOOD_ID_LIST
import com.database.Constants.ATTRIBUTE_PRODUCT_ID_LIST
import com.database.Constants.ATTRIBUTE_SERVICE_ID_LIST
import com.database.Constants.FOOD_COLLECTION
import com.database.Constants.PRODUCT_COLLECTION
import com.database.Constants.SERVICE_COLLECTION
import com.database.Constants.TEST_FOOD_COLLECTION
import com.database.Constants.TEST_PRODUCT_COLLECTION
import com.database.Constants.TEST_SERVICE_COLLECTION
import com.database.Constants.TEST_USER_COLLECTION
import com.database.Constants.USER_COLLECTION
import com.domain.model.Food
import com.domain.model.Product
import com.domain.model.Service
import com.domain.model.UserInfo
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.session.LoggedUser

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

    fun fetchUserDataForSessionModule(
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

    fun saveProduct(
        newProduct: Product,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        val newProductDocumentReference = getProductCollection().document()
        val userDocumentReference = getUserCollection().document(LoggedUser.info.email)
        val newProductId = newProductDocumentReference.id
        val newUserProductIdList = LoggedUser.info.productIdList + newProductId
        newProductDocumentReference.set(newProduct).addOnSuccessListener {
            userDocumentReference.update(ATTRIBUTE_PRODUCT_ID_LIST, newUserProductIdList)
                .addOnSuccessListener {
                    LoggedUser.info.productIdList = newUserProductIdList
                    onSuccess.invoke()
                }
                .addOnFailureListener {
                    onFailure.invoke()
                }
        }.addOnFailureListener {
            onFailure.invoke()
        }
    }

    fun saveService(
        newService: Service,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        val newServiceDocumentReference = getServiceCollection().document()
        val userDocumentReference = getUserCollection().document(LoggedUser.info.email)
        val newServiceId = newServiceDocumentReference.id
        val newUserServiceIdList = LoggedUser.info.serviceIdList + newServiceId
        newServiceDocumentReference.set(newService).addOnSuccessListener {
            userDocumentReference.update(ATTRIBUTE_SERVICE_ID_LIST, newUserServiceIdList)
                .addOnSuccessListener {
                    LoggedUser.info.serviceIdList = newUserServiceIdList
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

    private fun getServiceCollection(): CollectionReference = getFirestoreInstance().collection(
        if (BuildConfig.DEBUG) TEST_SERVICE_COLLECTION else SERVICE_COLLECTION
    )

    private fun getProductCollection(): CollectionReference = getFirestoreInstance().collection(
        if (BuildConfig.DEBUG) TEST_PRODUCT_COLLECTION else PRODUCT_COLLECTION
    )
}