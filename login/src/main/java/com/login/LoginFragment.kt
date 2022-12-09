package com.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.login.databinding.FragmentLoginBinding
import com.qds.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginJourneySharedViewModel by sharedViewModel()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.root.feathersAnimation(1500L)
        binding.buttonLogin.setOnClickListenerWithAnimation(300L) {

        }
        binding.mainEditTextEmail.editText.animateFocus(150L)
        binding.mainEditTextPassword.editText.animateFocus(150L)
        return binding.root
    }
}