package com.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.domain.model.Food
import com.domain.model.Product
import com.domain.model.Service
import com.home.adapter.food.FoodsAdapter
import com.home.adapter.product.ProductsAdapter
import com.home.adapter.service.ServicesAdapter
import com.home.databinding.FragmentHomeBinding
import com.home.viewModel.HomeViewModel
import com.qds.setOnClickListenerWithAnimation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.imageViewCake.setOnClickListenerWithAnimation {
            viewModel.saveFood(Food(title = "Bolo delicioso"))
        }
        binding.imageViewCommode.setOnClickListenerWithAnimation {
            viewModel.saveProduct(Product(title = "Comoda absurdamente espaçosa"))
        }
        binding.imageViewToolbox.setOnClickListenerWithAnimation {
            viewModel.saveService(Service(title = "Eletricista a 100m de você"))
        }
        setupObservers()
        fetchItems()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.foods.observe(viewLifecycleOwner) {
            setupFoodsRecyclerView(it)
        }
        viewModel.products.observe(viewLifecycleOwner) {
            setupProductsRecyclerView(it)
        }
        viewModel.services.observe(viewLifecycleOwner) {
            setupServicesRecyclerView(it)
        }
    }

    private fun setupFoodsRecyclerView(foods: List<Food>) {
        binding.recyclerViewFirstCategory.apply {
            adapter = FoodsAdapter(foods)
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupProductsRecyclerView(products: List<Product>) {
        binding.recyclerViewSecondCategory.apply {
            adapter = ProductsAdapter(products)
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupServicesRecyclerView(services: List<Service>) {
        binding.recyclerViewThirdCategory.apply {
            adapter = ServicesAdapter(services)
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.HORIZONTAL, false)
        }
    }

    private fun fetchItems() {
        viewModel.fetchItems()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}