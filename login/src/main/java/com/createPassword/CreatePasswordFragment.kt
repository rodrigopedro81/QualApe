package com.createPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.FieldsState
import com.authentication.Authentication
import com.domain.commons.Constants.AnimationDurations.BUTTON_DURATION
import com.domain.commons.Constants.AnimationDurations.FEATHERS_DURATION
import com.domain.commons.Verifier.verifyPassword
import com.LoginViewModel
import com.login.R
import com.login.databinding.FragmentCreatePasswordBinding
import com.qds.feathersAnimation
import com.qds.setOnClickListenerWithAnimation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CreatePasswordFragment : Fragment() {

    private var _binding: FragmentCreatePasswordBinding? = null
    private val binding get() = _binding!!
    private val passwordField get() = binding.editTextPassword
    private val confirmPasswordField get() = binding.editTextConfirmPassword
    private val viewModel: LoginViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePasswordBinding.inflate(inflater)
        setupObserver()
        checkFields()
        with(binding) {
            root.feathersAnimation(FEATHERS_DURATION)
            buttonGoBack.setOnClickListenerWithAnimation(BUTTON_DURATION) {
                activity?.onBackPressed()
            }
            buttonCreateAccount.setOnClickListenerWithAnimation(BUTTON_DURATION) {
                if (viewModel.fieldsState.value is FieldsState.FieldsAreValid) {
                    with(viewModel.user) {
                        Authentication.register(
                            email,
                            passwordField.text
                        ) { isSuccessful, errorMessage ->
                            if (isSuccessful) {
                                handleSuccess()
                            } else {
                                showError(errorMessage)
                            }
                        }
                    }
                }

            }
        }
        setupFieldListeners()
        return binding.root
    }

    private fun setupObserver() {
        viewModel.fieldsState.observe(viewLifecycleOwner) { state ->
            when (state){
                FieldsState.FieldsAreInvalid -> binding.buttonCreateAccount.disableButton()
                FieldsState.FieldsAreValid -> binding.buttonCreateAccount.enableButton()
            }
        }
    }

    private fun showError(errorMessage: String) {
        // TODO () -> Mostrar Toast de erro com o por quê
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun handleSuccess() {
        findNavController().navigate(R.id.action_createPasswordFragment_to_loginFragment)
    }

    private fun setupFieldListeners() {
        passwordField.setValidationScript(
            validationRule = { text ->
                verifyPassword(text)
            },
            doAfter = { checkFields() }
        )
        confirmPasswordField.setValidationScript(
            validationRule = { text ->
                verifyPassword(text)
            },
            doAfter = { checkFields() }
        )
    }

    private fun passwordsAreEqual() = passwordField.text == confirmPasswordField.text

    private fun checkFields() {
        if (passwordsAreEqual()) {
            viewModel.setFieldsAsValid()
        } else {
            viewModel.setFieldsAsInvalid()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}