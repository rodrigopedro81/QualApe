package com.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.authentication.Authentication
import com.domain.commons.Constants.AnimationDurations.BUTTON_DURATION
import com.domain.commons.Constants.AnimationDurations.FADE_DURATION
import com.domain.commons.Constants.AnimationDurations.FEATHERS_DURATION
import com.domain.commons.Verifier.isEmailValid
import com.domain.commons.Verifier.isPasswordValid
import com.login.databinding.FragmentLoginBinding
import com.qds.MainDialog.Companion.buildMainDialog
import com.qds.fadeOut
import com.qds.feathersAnimation
import com.qds.setOnClickListenerWithAnimation

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater)
        with(binding) {
            updateLoginButtonState()
            root.feathersAnimation(FEATHERS_DURATION)
            mainButtonCreateAccount.setOnClickListenerWithAnimation(BUTTON_DURATION) {
                root.fadeOut(FADE_DURATION) {
                    findNavController().navigate(R.id.loginFragmentToRegisterFragment)
                }
            }
            mainButtonLogin.setOnClickListenerWithAnimation(BUTTON_DURATION) {
                if (allFieldsAreValid()) {
                    Authentication.login(
                        email = mainEditTextEmailLayout.text,
                        password = mainEditTextPasswordLayout.text,
                        callback = { isSuccessful, errorMessage ->
                            handleLoginAuth(isSuccessful, errorMessage)
                        }
                    )
                }
            }
            mainEditTextEmailLayout.editText.doAfterTextChanged {
                if (it.toString().isNotEmpty()) {
                    if (it.toString().isEmailValid()) {
                        mainEditTextEmailLayout.setEditTextAsValid()
                    } else {
                        mainEditTextEmailLayout.setEditTextAsInvalid()
                    }
                    updateLoginButtonState()
                }
            }
            mainEditTextPasswordLayout.editText.doAfterTextChanged {
                if (it.toString().isNotEmpty()) {
                    if (it.toString().isPasswordValid()) {
                        mainEditTextPasswordLayout.setEditTextAsValid()
                    } else {
                        mainEditTextPasswordLayout.setEditTextAsInvalid()
                    }
                    updateLoginButtonState()
                }
            }
        }
        return binding.root
    }

    private fun updateLoginButtonState() {
        if (allFieldsAreValid()) {
            binding.mainButtonLogin.enableButton()
        } else {
            binding.mainButtonLogin.disableButton()
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
            mainEditTextEmailLayout.fieldIsValid && mainEditTextPasswordLayout.fieldIsValid
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