package com.delaroystudios.vpd.users

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.delaroystudios.vpd.MainActivity
import com.delaroystudios.vpd.databinding.AddUserBinding
import com.delaroystudios.vpd.databinding.UsersListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddUser : Fragment() {
    private lateinit var binding: AddUserBinding
    private val viewModel: UsersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = AddUserBinding.inflate(inflater, container, false)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.addNewUser.setOnClickListener {
            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val username = binding.username.text.toString()
            val phoneNumber = binding.phone.text.toString()
            val website = binding.website.text.toString()

            when {
                name.isEmpty() -> {
                    Toast.makeText(requireContext(), "Name is required", Toast.LENGTH_SHORT).show()
                }
                username.isEmpty() -> {
                    Toast.makeText(requireContext(), "Username is required", Toast.LENGTH_SHORT).show()
                }
                email.isEmpty() -> {
                    Toast.makeText(requireContext(), "Email is required", Toast.LENGTH_SHORT).show()
                }
                !isValidEmail(email) -> {
                    Toast.makeText(requireContext(), "Email is invalid", Toast.LENGTH_SHORT).show()
                }
                website.isEmpty() -> {
                    Toast.makeText(requireContext(), "Website is required", Toast.LENGTH_SHORT).show()
                }
                phoneNumber.isEmpty() -> {
                    Toast.makeText(requireContext(), "Phone number is required", Toast.LENGTH_SHORT).show()
                }

                else -> {
                   viewModel.addUser(
                       id = generateId(),
                       name = name,
                       username = username,
                       email = email,
                       phone = phoneNumber,
                       website = website,
                       imageUri = ""
                   )
                    Toast.makeText(requireContext(), "added successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            }
        }

        return binding.root
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun generateId(): Int {
        val rnd = Random()
        val number = rnd.nextInt(999999)

        // this will convert any number sequence into 6 character.
        return String.format(Locale.US,"%06d", number).toInt()
    }
}