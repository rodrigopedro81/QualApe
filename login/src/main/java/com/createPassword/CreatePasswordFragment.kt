package com.createPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.domain.commons.Constants.AnimationDurations.BUTTON_DURATION
import com.login.LoginJourneySharedViewModel
import com.login.databinding.FragmentCreatePasswordBinding
import com.qds.feathersAnimation
import com.qds.setOnClickListenerWithAnimation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CreatePasswordFragment: Fragment() {

    private lateinit var binding: FragmentCreatePasswordBinding
    private val passwordField get() =  binding.editTextPassword.editText
    private val confirmPasswordField get() =  binding.editTextConfirmPassword.editText
    private val viewModel: LoginJourneySharedViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePasswordBinding.inflate(inflater)
        binding.root.feathersAnimation(1500L)
        binding.buttonCreateAccount.disableButton()
        setupButtons()
        setupFieldListeners()
        return binding.root
    }

    private fun setupFieldListeners() {
        passwordField.doAfterTextChanged {
            updateState()
        }
        confirmPasswordField.doAfterTextChanged {
            updateState()
        }
    }

    private fun updateState() {
        if (passwordField.text == confirmPasswordField.text) {
            binding.buttonCreateAccount.enableButton()
        }
    }

    private fun setupButtons() {
        // TODO () -> Criar a conta no firebase ao clicar no criar conta e depois navegar para home
        binding.buttonGoBack.setOnClickListenerWithAnimation(BUTTON_DURATION) {
            activity?.onBackPressed()
        }
    }
}