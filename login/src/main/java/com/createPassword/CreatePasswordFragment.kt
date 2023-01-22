package com.createPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.authentication.Authentication
import com.domain.commons.Constants.AnimationDurations.BUTTON_DURATION
import com.domain.commons.Constants.AnimationDurations.FEATHERS_DURATION
import com.LoginViewModel
import com.database.Database
import com.domain.commons.Verifier.isPasswordValid
import com.domain.model.User
import com.login.R
import com.login.databinding.FragmentCreatePasswordBinding
import com.qds.feathersAnimation
import com.qds.setOnClickListenerWithAnimation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CreatePasswordFragment : Fragment() {

    private var _binding: FragmentCreatePasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePasswordBinding.inflate(inflater)
        with(binding) {
            root.feathersAnimation(FEATHERS_DURATION)
            mainButtonGoBack.setOnClickListenerWithAnimation(BUTTON_DURATION) {
                activity?.onBackPressed()
            }
            mainButtonCreateAccount.setOnClickListenerWithAnimation(BUTTON_DURATION) {
                if (allFieldsAreValid()) {
                    with(viewModel.user) {
                        Authentication.register(
                            email, binding.mainEditTextCreatePassword.text
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
        with(binding) {
            mainEditTextCreatePassword.editText.doAfterTextChanged {
                if (it.toString().isNotEmpty()) {
                    if (it.toString().isPasswordValid()) {
                        mainEditTextCreatePassword.setEditTextAsValid()
                    } else {
                        mainEditTextCreatePassword.setEditTextAsInvalid()
                    }
                    updateCreatePasswordButtonState()
                }
            }
            mainEditTextConfirmPassword.editText.doAfterTextChanged {
                if (it.toString().isNotEmpty()) {
                    if (it.toString().isPasswordValid()) {
                        mainEditTextConfirmPassword.setEditTextAsValid()
                    } else {
                        mainEditTextConfirmPassword.setEditTextAsInvalid()
                    }
                    updateCreatePasswordButtonState()
                }
            }
        }
    }

    private fun allFieldsAreValid() = with(binding) {
        mainEditTextCreatePassword.text == mainEditTextConfirmPassword.text
                && mainEditTextCreatePassword.fieldIsValid
                && mainEditTextConfirmPassword.fieldIsValid
    }

    private fun updateCreatePasswordButtonState() {
        if (allFieldsAreValid()) {
            binding.mainButtonCreateAccount.enableButton()
        } else {
            binding.mainButtonCreateAccount.disableButton()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}