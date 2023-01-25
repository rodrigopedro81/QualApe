package com.firestore

import com.domain.model.Food
import com.domain.model.Product
import com.domain.model.Service
import com.domain.model.UserInfo

interface FirestoreRepository {

    fun saveUserData(
        userInfo: UserInfo,
        onComplete: (wasSuccessful: Boolean) -> Unit
    )

    fun fetchUserDataForSessionModule(
        userEmail: String,
        onComplete: (wasSuccessful: Boolean) -> Unit
    )

    fun saveFood(
        newFood: Food,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )

    fun saveProduct(
        newProduct: Product,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )

    fun saveService(
        newService: Service,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )

    fun getFoods(
        onComplete: (foodList: List<Food>?) -> Unit,
    )

    fun getServices(
        onComplete: (serviceList: List<Service>?) -> Unit,
    )

    fun getProducts(
        onComplete: (productList: List<Product>?) -> Unit,
    )
}