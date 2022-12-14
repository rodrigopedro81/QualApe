package com.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.domain.commons.Constants.AnimationDurations.BUTTON_DURATION
import com.domain.commons.Verifier.verifyApartment
import com.domain.commons.Verifier.verifyBlock
import com.domain.commons.Verifier.verifyEmail
import com.domain.commons.Verifier.verifyName
import com.domain.commons.Verifier.verifyWpp
import com.domain.model.User
import com.login.FieldsState
import com.login.LoginViewModel
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
        viewModel.setFieldsAsInvalid()
        setupObserver()
        setupFieldListeners()
        with(binding) {
            advanceButton.setOnClickListenerWithAnimation(BUTTON_DURATION) {
                if (viewModel.fieldsState.value is FieldsState.FieldsAreValid) {
                    val user = User(
                        name = name.text,
                        email = email.text,
                        wpp = wpp.text,
                        block = block.text,
                        apartment = apNumber.text
                    )
                    viewModel.saveUserInfo(user)
                    navigateToCreatePasswordFragment()
                }
            }
            buttonGoLogin.setOnClickListenerWithAnimation(BUTTON_DURATION) {
                findNavController().navigate(R.id.registerFragmentToLoginFragment)
            }
        }
        return binding.root
    }

    private fun setupFieldListeners() {
        with(binding) {
            name.setValidationScript(
                validationRule = {
                    verifyName(it)
                },
                doAfter = { checkFields() }
            )
            email.setValidationScript(
                validationRule = {
                    verifyEmail(it)
                },
                doAfter = { checkFields() }
            )
            apNumber.setValidationScript(
                validationRule = {
                    verifyApartment(it)
                },
                doAfter = { checkFields() }
            )
            block.setValidationScript(
                validationRule = {
                    verifyBlock(it)
                },
                doAfter = { checkFields() }
            )
            wpp.setValidationScript(
                validationRule = {
                    verifyWpp(it)
                },
                doAfter = { checkFields() }
            )
        }
    }

    private fun checkFields() {
        if (allFieldsAreValid()) {
            viewModel.setFieldsAsValid()
        } else {
            viewModel.setFieldsAsInvalid()
        }
    }

    private fun allFieldsAreValid(): Boolean = with(binding) {
        listOf(name, apNumber, wpp, block, email).all { it.fieldIsValid }
    }

    private fun setupObserver() {
        viewModel.fieldsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                FieldsState.FieldsAreInvalid -> binding.advanceButton.disableButton()
                FieldsState.FieldsAreValid -> binding.advanceButton.enableButton()
            }
        }
    }

    private fun navigateToCreatePasswordFragment() {
        findNavController().navigate(R.id.registerFragmentToCreatePasswordFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}