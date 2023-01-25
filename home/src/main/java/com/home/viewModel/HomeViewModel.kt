package com.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firestore.FirestoreRepository
import com.domain.model.Food
import com.domain.model.Product
import com.domain.model.Service

class HomeViewModel(
    private val database: FirestoreRepository
): ViewModel() {

    private val _products : MutableLiveData<List<Product>> = MutableLiveData()
    val products : LiveData<List<Product>>
        get () = _products
    private val _services : MutableLiveData<List<Service>> = MutableLiveData()
    val services : LiveData<List<Service>>
        get () = _services
    private val _foods : MutableLiveData<List<Food>> = MutableLiveData()
    val foods : LiveData<List<Food>>
        get () = _foods

    fun fetchItems() {
        database.run {
            getServices { _services.value = it }
            getProducts { _products.value = it }
            getFoods { _foods.value = it }
        }
    }

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
