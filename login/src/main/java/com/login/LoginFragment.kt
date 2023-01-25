package com.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.domain.commons.Verifier.isEmailValid
import com.domain.commons.Verifier.isPasswordValid
import com.login.databinding.FragmentLoginBinding
import com.domain.Routes
import com.domain.navigateWithAction
import com.domain.navigateWithRoute
import com.qds.MainDialog.Companion.buildMainDialog
import com.qds.fadeOut
import com.qds.feathersAnimation
import com.qds.setOnClickListenerWithAnimation
import com.viewModel.LoginViewModule
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel : LoginViewModule by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater)
        updateLoginButtonState()
        binding.root.feathersAnimation()
        setupButtons()
        setupEditTextListeners()
        viewModel.checkIfUserIsLoggedIn { isLoggedIn, userEmail ->
            if (isLoggedIn) userEmail?.let { startHomeModule() }
        }
        return binding.root
    }

    private fun setupButtons() {
        with(binding) {
            mainButtonCreateAccount.setOnClickListenerWithAnimation {
                root.fadeOut {
                    navigateWithAction(R.id.loginFragmentToRegisterFragment)
                }
            }
            mainButtonLogin.setOnClickListenerWithAnimation {
                if (allFieldsAreValid()) {
                    viewModel.login(
                        email = mainEditTextEmailLayout.text,
                        password = mainEditTextPasswordLayout.text,
                        callback = { isSuccessful, errorMessage ->
                            handleLoginAuth(isSuccessful, errorMessage)
                        }
                    )
                }
            }
        }
    }

    private fun setupEditTextListeners() {
        with(binding) {
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
            startHomeModule()
        } else {
            showError(errorMessage)
        }
    }

    private fun startHomeModule() {
        viewModel.fetchUserDataForSessionModule { fetchedSuccessfully ->
            if (fetchedSuccessfully)
                navigateWithRoute(Routes.HOME_FRAGMENT_ROUTE)
            else
                showError("")
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