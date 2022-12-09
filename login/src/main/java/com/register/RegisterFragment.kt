package com.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.domain.model.User
import com.login.LoginJourneySharedViewModel
import com.login.R
import com.login.databinding.FragmentRegisterBinding
import com.qds.feathersAnimation
import com.qds.setOnClickListenerWithAnimation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterFragment : Fragment() {

    private val viewModel: LoginJourneySharedViewModel by sharedViewModel()
    private lateinit var binding: FragmentRegisterBinding
    private val name get() = binding.name.editText
    private val email get() = binding.email.editText
    private val wpp get() = binding.wpp.editText
    private val block get() = binding.block.editText
    private val apartment get() = binding.apNumber.editText
    private var nameValid = false
    private var emailValid = false
    private var wppValid = false
    private var blockValid = false
    private var apartmentValid = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        binding.root.feathersAnimation(1500L)
        binding.advanceButton.disableButton()
        setupFieldListeners()
        setupButtons()
        return binding.root
    }

    private fun allFieldsAreValid() =
        nameValid && emailValid && wppValid && blockValid && apartmentValid

    private fun setupButtons() {
        binding.advanceButton.setOnClickListenerWithAnimation(300L) {
            if (allFieldsAreValid()) {
                val user =
                    User(
                        name.text.toString(),
                        email.text.toString(),
                        wpp.text.toString(),
                        block.text.toString(),
                        apartment.text.toString()
                    )
                viewModel.saveUserInfo(user)
                navigateToCreatePasswordFragment()
            }
        }
    }

    private fun navigateToCreatePasswordFragment() {
        findNavController().navigate(R.id.registerFragmentToCreatePasswordFragment)
    }

    private fun setupFieldListeners() {
        name.doAfterTextChanged {
            nameValid = verifyName(it.toString())
        }
        email.doAfterTextChanged {
            emailValid = verifyEmail(it.toString())
        }
        wpp.doAfterTextChanged {
            wppValid = verifyWpp(it.toString())
        }
        block.doAfterTextChanged {
            blockValid = verifyBlock(it.toString())
        }
        apartment.doAfterTextChanged {
            apartmentValid = verifyApartment(it.toString())
        }
    }

    private fun verifyWpp(wpp: String): Boolean =
        android.util.Patterns.PHONE.matcher(wpp).matches()

    private fun verifyApartment(apartment: String): Boolean =
        (apartment.toInt() in 100..516)

    private fun verifyBlock(block: String): Boolean =
        (block.toInt() in 1..9)

    private fun verifyEmail(email: String): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun verifyName(name: String): Boolean =
        name.length > 6
}