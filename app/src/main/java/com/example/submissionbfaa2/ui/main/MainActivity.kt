package com.example.submissionbfaa2.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionbfaa2.R
import com.example.submissionbfaa2.data.response.UserItem
import com.example.submissionbfaa2.databinding.ActivityMainBinding
import com.example.submissionbfaa2.helper.createSearchViewMenu
import com.example.submissionbfaa2.ui.detail.UserDetailActivity
import com.example.submissionbfaa2.ui.favorite.FavoriteUserActivity
import com.example.submissionbfaa2.ui.main.adapter.UserListAdapter
import com.example.submissionbfaa2.ui.settings.SettingActivity

class MainActivity : AppCompatActivity(), UserListAdapter.OnUserClickCallback {

    companion object {
        fun start(context: Context) {
            Intent(context, MainActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserListAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = resources.getString(R.string.app_name)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        mainViewModel.getSearchUser().observe(this, {
            if (it != null) {
                updateListData(it)
                showLoading(false)
            }
        })

        userAdapter = UserListAdapter(this, mutableListOf(), this)
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = userAdapter
        }
        binding.welcomeText.visibility = View.VISIBLE
    }

    private fun searchUser(query: String) {
        showLoading(true)
        mainViewModel.searchUser(query)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateListData(list: List<UserItem>) {
        userAdapter.listUser.clear()
        userAdapter.listUser.addAll(list)
        userAdapter.notifyDataSetChanged()
        if (list.isEmpty()) {
            binding.rvUser.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
        } else {
            binding.tvNoData.visibility = View.GONE
        }
    }

    override fun onUserClicked(data: UserItem) {
        UserDetailActivity.start(this, data.login)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.rvUser.visibility = View.GONE
            binding.welcomeText.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.welcomeText.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting) {
            val mIntent = Intent(this@MainActivity, SettingActivity::class.java)
            startActivity(mIntent)
        }
        if (item.itemId == R.id.favorite) {
            val mIntent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        createSearchViewMenu(menu) {
            searchUser(it)
        }
        return super.onCreateOptionsMenu(menu)
    }
}