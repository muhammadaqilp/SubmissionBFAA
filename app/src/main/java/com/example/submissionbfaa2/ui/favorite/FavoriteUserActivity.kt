package com.example.submissionbfaa2.ui.favorite

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionbfaa2.R
import com.example.submissionbfaa2.databinding.ActivityFavoriteUserBinding
import com.example.submissionbfaa2.helper.ViewModelFactory
import com.example.submissionbfaa2.ui.detail.UserDetailActivity
import com.example.submissionbfaa2.ui.favorite.adapter.FavoriteUserAdapter

class FavoriteUserActivity : AppCompatActivity(), FavoriteUserAdapter.OnFavoriteClickCallback {

    private var _activityFavoriteUserBinding: ActivityFavoriteUserBinding? = null
    private val binding get() = _activityFavoriteUserBinding
    private lateinit var adapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteUserBinding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = resources.getString(R.string.favorite_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val favViewModel = obtainViewModel(this)
        favViewModel.getAllListFavorite().observe(this, { favList ->
            if (favList != null) {
                adapter.setListFavorite(favList)
            }
        })

        adapter = FavoriteUserAdapter(this)
        binding?.rvFavorite?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavorite?.setHasFixedSize(true)
        binding?.rvFavorite?.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteUserViewModel::class.java)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteUserBinding = null
    }

    override fun onFavoriteClicked(username: String?) {
        if (username != null) {
            UserDetailActivity.start(this, username)
        }
    }
}