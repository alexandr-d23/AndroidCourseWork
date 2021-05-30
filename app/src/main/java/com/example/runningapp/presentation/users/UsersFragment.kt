package com.example.runningapp.presentation.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.runningapp.ApplicationDelegate
import com.example.runningapp.databinding.FragmentUsersBinding
import com.example.runningapp.utils.Constants
import javax.inject.Inject

class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding: FragmentUsersBinding get() = _binding!!
    private lateinit var adapter: UserAdapter

    @Inject
    lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApplicationDelegate.getScreenComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsersBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserAdapter(
            itemClick = {
                navigateToUserDetails(it)
            },
            subscribeClick = {
                viewModel.subscribeClick(it)
            },
            unsubscribeClick = {
                viewModel.unsubscribeClick(it)
            }
        )
        binding.rvUsers.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        initLiveDataListeners()
        initListeners()
    }

    private fun initListeners() {
        initSpinner()
    }

    private fun initSpinner() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                i: Int,
                l: Long
            ) {
                when (i) {
                    Constants.ALL_USERS_ARRAY_ID -> {
                        viewModel.getAllUsers()
                    }
                    Constants.SUBSCRIBED_USERS_ARRAY_ID -> {
                        viewModel.getSubscribedUsers()
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun initLiveDataListeners() {
        observeUsers()
    }

    private fun observeUsers() {
        viewModel.getUsers().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun navigateToUserDetails(userId: String){
        val action = UsersFragmentDirections.actionUsersFragmentToUserDetailsFragment(userId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}