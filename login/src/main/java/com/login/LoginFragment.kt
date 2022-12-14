package com.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.authentication.Authentication
import com.domain.commons.Constants.AnimationDurations.BUTTON_DURATION
import com.domain.commons.Constants.AnimationDurations.FADE_DURATION
import com.domain.commons.Constants.AnimationDurations.FEATHERS_DURATION
import com.domain.commons.Verifier.verifyEmail
import com.domain.commons.Verifier.verifyPassword
import com.login.databinding.FragmentLoginBinding
import com.qds.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        setupObserver()
        with(binding) {
            root.feathersAnimation(FEATHERS_DURATION)
            buttonCreateAccount.setOnClickListenerWithAnimation(BUTTON_DURATION) {
                root.fadeOut(FADE_DURATION) {
                    findNavController().navigate(R.id.loginFragmentToRegisterFragment)
                }
            }
            buttonLogin.setOnClickListenerWithAnimation(BUTTON_DURATION) {
                if (viewModel.loginState.value is LoginState.FieldsAreValid) {
                    Authentication.login(
                        email = mainEditTextEmail.text,
                        password = mainEditTextPassword.text,
                        callback = ::handleLoginAuth
                    )
                }
            }
            mainEditTextEmail.setValidationScript(
                validationRule = { text ->
                    verifyEmail(text)
                },
                doAfter = { checkFields() }
            )
            mainEditTextPassword.setValidationScript(
                validationRule = { text ->
                    verifyPassword(text)
                },
                doAfter = { checkFields() }
            )
        }
        return binding.root
    }

    private fun checkFields() {
        if (allFieldsAreValid()) {
            viewModel.setFieldsAsValid()
        } else {
            viewModel.setFieldsAsInvalid()
        }
    }

    private fun setupObserver() {
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                LoginState.FieldsAreInvalid -> binding.buttonLogin.disableButton()
                LoginState.FieldsAreValid -> binding.buttonLogin.enableButton()
            }
        }
    }

    private fun handleLoginAuth(isSuccessful: Boolean, errorMessage: String) {
        if (isSuccessful) navigateToHome() else showError(errorMessage)
    }

    private fun showError(errorMessage: String) {
        // TODO () -> Mostrar Toast de erro com o por quê
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToHome() {
        // TODO () -> Navegar para a Home
        Toast.makeText(context, "Logou com sucesso!", Toast.LENGTH_SHORT).show()
    }

    private fun allFieldsAreValid(): Boolean {
        return with(binding) {
            mainEditTextEmail.fieldIsValid && mainEditTextPassword.fieldIsValid
        }
    }
}