package com.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.FieldsState
import com.LoginViewModel
import com.authentication.Authentication
import com.domain.commons.Constants.AnimationDurations.BUTTON_DURATION
import com.domain.commons.Constants.AnimationDurations.FADE_DURATION
import com.domain.commons.Constants.AnimationDurations.FEATHERS_DURATION
import com.domain.commons.Verifier.verifyEmail
import com.domain.commons.Verifier.verifyPassword
import com.login.databinding.FragmentLoginBinding
import com.qds.*
import com.qds.MainDialog.Companion.buildMainDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by sharedViewModel()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater)
        setupObserver()
        checkFields()
        with(binding) {
            root.feathersAnimation(FEATHERS_DURATION)
            mainButtonCreateAccount.setOnClickListenerWithAnimation(BUTTON_DURATION) {
                root.fadeOut(FADE_DURATION) {
                    findNavController().navigate(R.id.loginFragmentToRegisterFragment)
                }
            }
            mainButtonLogin.setOnClickListenerWithAnimation(BUTTON_DURATION) {
                if (viewModel.fieldsState.value is FieldsState.FieldsAreValid) {
                    Authentication.login(
                        email = mainEditTextEmail.text,
                        password = mainEditTextPassword.text,
                        callback = { isSuccessful, errorMessage ->
                            handleLoginAuth(isSuccessful, errorMessage)
                        }
                    )
                }
            }
            mainEditTextEmail.setValidationRule(
                validationRule = { text ->
                    verifyEmail(text)
                },
                doAfter = { checkFields() }
            )
            mainEditTextPassword.setValidationRule(
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
        viewModel.fieldsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                FieldsState.FieldsAreInvalid -> binding.mainButtonLogin.disableButton()
                FieldsState.FieldsAreValid -> binding.mainButtonLogin.enableButton()
            }
        }
    }

    private fun handleLoginAuth(isSuccessful: Boolean, errorMessage: String?) {
        if (isSuccessful) navigateToHome() else showError(errorMessage)
    }

    private fun showError(errorMessage: String?) {
        errorMessage?.let {
            context?.buildMainDialog(
                description = errorMessage,
                buttonClickListener = { it.dismiss() },
                buttonText = ERROR_DIALOG_BUTTON
            )
        }
    }

    private fun navigateToHome() {
        val request = NavDeepLinkRequest.Builder
            .fromUri(HOME_FRAGMENT_ROUTE.toUri())
            .build()
        findNavController().navigate(request)
    }

    private fun allFieldsAreValid(): Boolean {
        return with(binding) {
            mainEditTextEmail.fieldIsValid && mainEditTextPassword.fieldIsValid
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ERROR_DIALOG_BUTTON = "Ok"
        private const val HOME_FRAGMENT_ROUTE = "qualape-app://home_module/home_fragment"
    }
}