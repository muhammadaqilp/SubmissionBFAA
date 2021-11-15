package com.example.submissionbfaa2.ui.detail.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionbfaa2.ui.detail.UserDetailActivity
import com.example.submissionbfaa2.ui.main.adapter.UserListAdapter
import com.example.submissionbfaa2.data.response.UserItem
import com.example.submissionbfaa2.databinding.FragmentFollowListBinding
import com.example.submissionbfaa2.ui.main.MainViewModel

class FollowListFragment : Fragment(), UserListAdapter.OnUserClickCallback {

    private lateinit var binding: FragmentFollowListBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var userAdapter: UserListAdapter

    private var type: String? = null
    private var username: String? = null

    companion object {
        @JvmStatic
        fun newInstance(type: String, username: String) =
            FollowListFragment().apply {
                arguments = Bundle().apply {
                    putString("type", type)
                    putString("username", username)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString("type", "")
            username = it.getString("username", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        mainViewModel.getFollowers().observe(viewLifecycleOwner, {
            if (it != null) {
                updateList(it)
                showLoading(false)
            }
        })

        mainViewModel.getFollowing().observe(viewLifecycleOwner, {
            if (it != null) {
                updateList(it)
                showLoading(false)
            }
        })

        userAdapter = UserListAdapter(requireContext(), mutableListOf(), this)
        binding.rvFollow.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = userAdapter
        }

        showLoading(true)
        getFollowList(username.orEmpty(), type.orEmpty())
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateList(list: List<UserItem>) {
        userAdapter.listUser.clear()
        userAdapter.listUser.addAll(list)
        userAdapter.notifyDataSetChanged()
        if (list.isEmpty()) {
            binding.rvFollow.visibility = View.GONE
            binding.tvNoDataFollow.visibility = View.VISIBLE
        } else {
            binding.tvNoDataFollow.visibility = View.GONE
        }
    }

    private fun getFollowList(username: String, type: String) {
        if (type == "followers") mainViewModel.listFollowers(username)
        else mainViewModel.listFollowing(username)
    }

    override fun onUserClicked(data: UserItem) {
        UserDetailActivity.start(requireContext(), data.login)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.rvFollow.visibility = View.GONE
            binding.progressBarFollow.visibility = View.VISIBLE
            binding.tvNoDataFollow.visibility = View.GONE
        } else {
            binding.rvFollow.visibility = View.VISIBLE
            binding.progressBarFollow.visibility = View.GONE
        }
    }
}