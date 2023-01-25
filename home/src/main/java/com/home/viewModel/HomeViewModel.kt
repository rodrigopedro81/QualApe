package com.home.viewModel

import androidx.lifecycle.ViewModel
import com.firestore.FirestoreRepository
import com.domain.model.Food
import com.domain.model.Product
import com.domain.model.Service

class HomeViewModel(
    private val database: FirestoreRepository
): ViewModel() {


    fun fetchItems() = listOf<Product>()

    fun saveService(
        service: Service,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        database.saveService(
            newService = service,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    fun saveProduct(
        product: Product,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        database.saveProduct(
            newProduct = product,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    fun saveFood(
        food: Food,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        database.saveFood(
            newFood = food,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}
