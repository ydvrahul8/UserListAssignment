package com.example.gitusers.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.gitusers.R
import com.example.gitusers.databinding.FragmentUserListBinding
import com.example.gitusers.domain.model.User
import com.example.gitusers.presentation.adapter.LoaderAdapter
import com.example.gitusers.presentation.adapter.UserPagingAdapter
import com.example.gitusers.presentation.viewmodel.UserViewModel
import com.example.gitusers.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private val viewModel by viewModels<UserViewModel>()
    private lateinit var adapter: UserPagingAdapter

    private lateinit var binding:FragmentUserListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = UserPagingAdapter { status, user ->
            userResponse(status, user)
        }
        adapter.addLoadStateListener { loadState->
            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            errorState?.let {
               showToast(requireContext(),"Error"+it)
            }
        }
        adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(adapter),
            footer = LoaderAdapter(adapter)
        )

        binding.recyclerView.adapter = adapter
        binding.swipe.setOnRefreshListener {
            adapter.refresh()
        }
        collectUser()
    }

    private fun collectUser(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userList.collectLatest {
                    adapter.submitData(lifecycle, it)
                    binding.swipe.isRefreshing = false
                }
            }
        }
    }

    private fun userResponse(status: String, user: User) {
        Toast.makeText(requireContext(), "${user.login} $status", Toast.LENGTH_SHORT).show()
    }

}