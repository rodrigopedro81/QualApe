package com.createPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.authentication.Authentication
import com.LoginViewModel
import com.database.Database
import com.domain.commons.Verifier.isPasswordValid
import com.domain.model.UserInfo
import com.login.R
import com.login.databinding.FragmentCreatePasswordBinding
import com.qds.MainDialog.Companion.buildMainDialog
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
            root.feathersAnimation()
            mainButtonGoBack.setOnClickListenerWithAnimation {
                activity?.onBackPressed()
            }
            mainButtonCreateAccount.setOnClickListenerWithAnimation {
                if (allFieldsAreValid()) {
                    with(viewModel.userInfo) {
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
        errorMessage?.let { error ->
            buildMainDialog(
                context = requireContext(),
                description = error,
                buttonClickListener = { it.dismiss() },
                buttonText = ERROR_DIALOG_BUTTON
            )
        }
    }

    private fun handleSuccess(userInfo: UserInfo) {
        Database.saveUserData(userInfo) { wasSuccessful ->
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

    companion object {
        private const val ERROR_DIALOG_BUTTON = "Ok"
    }
}