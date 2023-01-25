package com.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.domain.model.Food
import com.domain.model.Product
import com.domain.model.Service
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
        fetchItems()
        return binding.root
    }

    private fun fetchItems() {
        viewModel.fetchItems()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}