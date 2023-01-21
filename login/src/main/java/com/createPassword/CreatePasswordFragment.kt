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
import com.database.Database
import com.domain.model.User
import com.login.R
import com.login.databinding.FragmentCreatePasswordBinding
import com.qds.feathersAnimation
import com.qds.setOnClickListenerWithAnimation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CreatePasswordFragment : Fragment() {

    private var _binding: FragmentCreatePasswordBinding? = null
    private val binding get() = _binding!!
    private val passwordField get() = binding.mainEditTextCreatePassword
    private val confirmPasswordField get() = binding.mainEditTextConfirmPassword
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
            mainButtonGoBack.setOnClickListenerWithAnimation(BUTTON_DURATION) {
                activity?.onBackPressed()
            }
            mainButtonCreateAccount.setOnClickListenerWithAnimation(BUTTON_DURATION) {
                if (viewModel.fieldsState.value is FieldsState.FieldsAreValid) {
                    with(viewModel.user) {
                        Authentication.register(
                            email,
                            passwordField.text
                        ) { isSuccessful, errorMessage ->
                            if (isSuccessful) {
                                handleSuccess(this)
                            } else {
                                handleError(errorMessage)
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
                FieldsState.FieldsAreInvalid -> binding.mainButtonCreateAccount.disableButton()
                FieldsState.FieldsAreValid -> binding.mainButtonCreateAccount.enableButton()
            }
        }
    }

    private fun handleError(errorMessage: String?) {
        errorMessage?.let { Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show() }
    }

    private fun handleSuccess(user: User) {
        Database.saveUserData(user) { wasSuccessful ->
            if (wasSuccessful) {
                findNavController().navigate(R.id.action_createPasswordFragment_to_loginFragment)
            } else {
                // TODO () -> Arranjar forma recursiva inteligente de tentar novamente até conseguir
            }
        }
    }

    private fun setupFieldListeners() {
        passwordField.setValidationRule(
            validationRule = { text ->
                verifyPassword(text)
            },
            doAfter = { checkFields() }
        )
        confirmPasswordField.setValidationRule(
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