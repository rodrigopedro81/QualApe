package com.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.authentication.Authentication
import com.database.Database.fetchUserDataForSessionModule
import com.domain.commons.Verifier.isEmailValid
import com.domain.commons.Verifier.isPasswordValid
import com.login.databinding.FragmentLoginBinding
import com.navigation.Routes
import com.navigation.navigateWithAction
import com.navigation.navigateWithRoute
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
            root.feathersAnimation()
            mainButtonCreateAccount.setOnClickListenerWithAnimation {
                root.fadeOut {
                    navigateWithAction(R.id.loginFragmentToRegisterFragment)
                }
            }
            mainButtonLogin.setOnClickListenerWithAnimation {
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

    override fun onResume() {
        super.onResume()
        if (Authentication.userIsAuthenticated()) {
            Authentication.userEmail()?.let { userEmail ->
                buildMainDialog(
                    context = requireContext(),
                    title = "Você já está logado",
                    description = "Detectamos que você logou recentemente no app com o email: $userEmail ",
                    buttonClickListener = {
                        startHomeModule(userEmail)
                        it.dismiss()
                    },
                    buttonText = "Ok!"
                )
            }
        }
    }

    private fun updateLoginButtonState() {
        if (allFieldsAreValid()) {
            binding.mainButtonLogin.enableButton()
        } else {
            binding.mainButtonLogin.disableButton()
        }
    }

    private fun handleLoginAuth(isSuccessful: Boolean, errorMessage: String?) {
        if (isSuccessful) {
            val userEmail = binding.mainEditTextEmailLayout.text
            startHomeModule(userEmail)
        } else {
            showError(errorMessage)
        }
    }

    private fun startHomeModule(userEmail: String) {
        fetchUserDataForSessionModule(userEmail) { fetchedSuccessfully ->
            if (fetchedSuccessfully) navigateToHome() else showError("")
        }
    }

    private fun showError(errorMessage: String?) {
        errorMessage?.let { error ->
            buildMainDialog(
                context = requireContext(),
                description = error,
                buttonClickListener = { it.dismiss() },
                buttonText = ERROR_DIALOG_BUTTON
            )
        }
    }

    private fun navigateToHome() {
        navigateWithRoute(Routes.HOME_FRAGMENT_ROUTE)
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
    }
}