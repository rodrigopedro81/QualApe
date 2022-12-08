package com.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.login.LoginJourneyViewModel
import com.login.databinding.FragmentRegisterBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterFragment : Fragment() {

    private val viewModel: LoginJourneyViewModel by sharedViewModel()
    private lateinit var binding: FragmentRegisterBinding
    private val name get() = binding.name.editText
    private val email get() = binding.email.editText
    private val wpp get() = binding.wpp.editText
    private val block get() = binding.block.editText
    private val apartment get() = binding.apNumber.editText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        binding.advanceButton.disableButton()
        setupFieldListeners()
        return binding.root
    }

    private fun setupFieldListeners() {
        name.doAfterTextChanged {
//            verifyFields()
            verifyName(it.toString())
        }
        email.doAfterTextChanged {
            verifyEmail(it.toString())
        }
        wpp.doAfterTextChanged {
            verifyWpp(it.toString())
        }
        block.doAfterTextChanged {
            verifyBlock(it.toString())
        }
        apartment.doAfterTextChanged {
            verifyApartment(it.toString())
        }
    }

//    private fun verifyFields() {
//        if ()
//    }

//    private fun setupAdvanceButton() {
//        binding.advanceButton.setOnClickListener {
//            if (allFieldsAreValid()) {
//
//                Toast.makeText(context, "Pode avançar!", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(context, "Preencha corretamente todos os campos", Toast.LENGTH_LONG).show()
//            }
//        }
//    }

    private fun verifyWpp(wpp: String): Boolean {
        return true
    }

    private fun verifyApartment(apartment: String) {
        (apartment.toInt() in 100..516)
    }

    private fun verifyBlock(block: String): Boolean {
        return (block.toInt() in 1..9)
    }

    private fun verifyEmail(email: String): Boolean {
        return true
    }

    private fun isAnyNull(vararg content: String?): Boolean {
        return content.any { it == null }
    }

    private fun verifyName(name: String): Boolean {
        return name.length > 6
    }
}