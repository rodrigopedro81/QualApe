package com.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.database.Database
import com.domain.model.Food
import com.home.databinding.FragmentHomeBinding
import com.qds.setOnClickListenerWithAnimation

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.imageViewCake.setOnClickListenerWithAnimation {
            Database.saveFood(
                newFood = Food(title = "Bolo delicioso"),
                onSuccess = { Log.d("teste", "Completou e o resultado foi sucesso") }
            )
        }
        binding.imageViewCommode.setOnClickListenerWithAnimation {
            Database.saveFood(
                newFood = Food(title = "Comoda absurdamente espaçosa"),
                onSuccess = { Log.d("teste", "Completou e o resultado foi sucesso") }
            )
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}