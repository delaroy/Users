package com.delaroystudios.vpd.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.delaroystudios.domain.model.Users
import com.delaroystudios.vpd.MainActivity
import com.delaroystudios.vpd.R
import com.delaroystudios.vpd.databinding.UsersListFragmentBinding
import com.delaroystudios.vpd.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersListFragment : Fragment(), UsersListAdapter.RecyclerViewClickListener {

    private lateinit var binding: UsersListFragmentBinding
    private val viewModel: UsersViewModel by viewModels()
    private val usersListAdapter: UsersListAdapter by lazy { UsersListAdapter(this@UsersListFragment) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = UsersListFragmentBinding.inflate(inflater, container, false)

        binding.usersRecyclerview.apply {
            adapter = usersListAdapter
        }

        lifecycleScope.launchWhenCreated {
            viewModel.userState.collect{
                when (it) {
                    is UiState.Loading -> {
                        showProgress()
                    }
                    is UiState.Success -> {
                        hideProgress()
                    }
                    is UiState.Error -> {
                        hideProgress()
                    }
                    else -> {}
                }
            }
        }

        return binding.root

    }

    private fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progress.visibility = View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.users.collect {
                if (it.isNotEmpty()) {
                    hideProgress()
                }
                usersListAdapter.submitList(it)
            }
        }
    }

    override fun clickOnItem(data: Users) {
        val bundle = Bundle()
        bundle.putParcelable("data", data)
        findNavController().navigate(R.id.action_UsersListFragment_to_UserDetails, bundle)
    }
}