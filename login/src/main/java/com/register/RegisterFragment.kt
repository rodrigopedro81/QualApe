package com.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.LoginViewModel
import com.domain.commons.Constants.AnimationDurations.BUTTON_DURATION
import com.domain.commons.Verifier.isApartmentValid
import com.domain.commons.Verifier.isBlockValid
import com.domain.commons.Verifier.isEmailValid
import com.domain.commons.Verifier.isNameValid
import com.domain.commons.Verifier.isWhatsappValid
import com.domain.model.UserInfo
import com.login.R
import com.login.databinding.FragmentRegisterBinding
import com.qds.setOnClickListenerWithAnimation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterFragment : Fragment() {

    private val viewModel: LoginViewModel by sharedViewModel()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater)
        setupFieldListeners()
        updateRegisterButtonState()
        with(binding) {
            mainButtonAdvance.setOnClickListenerWithAnimation(BUTTON_DURATION) {
                if (allFieldsAreValid()) {
                    val userInfo = UserInfo(
                        name = mainEditTextName.text,
                        email = mainEditTextEmail.text,
                        wpp = mainEditTextWhatsapp.text,
                        block = mainEditTextBlock.text,
                        apartment = mainEditTextApartmentNumber.text
                    )
                    viewModel.saveUserInfo(userInfo)
                    navigateToCreatePasswordFragment()
                }
            }
            mainButtonGoLogin.setOnClickListenerWithAnimation(BUTTON_DURATION) {
                findNavController().navigate(R.id.registerFragmentToLoginFragment)
            }
        }
        return binding.root
    }

    private fun setupFieldListeners() {
        with(binding) {
            mainEditTextName.editText.doAfterTextChanged {
                if (it.toString().isNotEmpty()) {
                    if (it.toString().isNameValid()) {
                        mainEditTextName.setEditTextAsValid()
                    } else {
                        mainEditTextName.setEditTextAsInvalid()
                    }
                    updateRegisterButtonState()
                }
            }
            mainEditTextEmail.editText.doAfterTextChanged {
                if (it.toString().isNotEmpty()) {
                    if (it.toString().isEmailValid()) {
                        mainEditTextEmail.setEditTextAsValid()
                    } else {
                        mainEditTextEmail.setEditTextAsInvalid()
                    }
                    updateRegisterButtonState()
                }
            }
            mainEditTextWhatsapp.editText.doAfterTextChanged {
                if (it.toString().isNotEmpty()) {
                    if (it.toString().isWhatsappValid()) {
                        mainEditTextWhatsapp.setEditTextAsValid()
                    } else {
                        mainEditTextWhatsapp.setEditTextAsInvalid()
                    }
                    updateRegisterButtonState()
                }
            }
            mainEditTextBlock.editText.doAfterTextChanged {
                if (it.toString().isNotEmpty()) {
                    if (it.toString().isBlockValid()) {
                        mainEditTextBlock.setEditTextAsValid()
                    } else {
                        mainEditTextBlock.setEditTextAsInvalid()
                    }
                    updateRegisterButtonState()
                }
            }
            mainEditTextApartmentNumber.editText.doAfterTextChanged {
                if (it.toString().isNotEmpty()) {
                    if (it.toString().isApartmentValid()) {
                        mainEditTextApartmentNumber.setEditTextAsValid()
                    } else {
                        mainEditTextApartmentNumber.setEditTextAsInvalid()
                    }
                    updateRegisterButtonState()
                }
            }
        }
    }

    private fun updateRegisterButtonState() {
        if (allFieldsAreValid()) {
            binding.mainButtonAdvance.enableButton()
        } else {
            binding.mainButtonAdvance.disableButton()
        }
    }

    private fun allFieldsAreValid(): Boolean = with(binding) {
        listOf(
            mainEditTextName,
            mainEditTextEmail,
            mainEditTextWhatsapp,
            mainEditTextBlock,
            mainEditTextApartmentNumber
        ).all { it.fieldIsValid }
    }

    private fun navigateToCreatePasswordFragment() {
        findNavController().navigate(R.id.registerFragmentToCreatePasswordFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}