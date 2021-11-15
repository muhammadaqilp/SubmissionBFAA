package com.example.submissionbfaa2.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submissionbfaa2.R
import com.example.submissionbfaa2.data.response.UserItemDetail
import com.example.submissionbfaa2.database.FavoriteUser
import com.example.submissionbfaa2.databinding.ActivityUserDetailBinding
import com.example.submissionbfaa2.helper.Const
import com.example.submissionbfaa2.helper.ViewModelFactory
import com.example.submissionbfaa2.ui.detail.adapter.PagerAdapter
import com.example.submissionbfaa2.ui.detail.fragment.FollowListFragment
import com.example.submissionbfaa2.ui.main.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context, username: String) {
            Intent(context, UserDetailActivity::class.java).apply {
                this.putExtra(Const.USERNAME_KEY, username)
                context.startActivity(this)
            }
        }
    }

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var mainViewModel: MainViewModel
    private var username: String? = null
    private var favUser: FavoriteUser? = null
    private lateinit var favUserViewModel: FavUserInsDelViewModel
    private var userId: String? = null
    private var usernameFav: String? = null
    private var avatar: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        username = intent?.getStringExtra(Const.USERNAME_KEY)

        supportActionBar?.title = getString(R.string.detail_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        mainViewModel.getDetailUser().observe(this, {
            if (it != null) {
                updateUserData(it)
                showLoading(false)
            }
        })

        username?.let {
            showLoading(true)
            mainViewModel.detailUser(it)
        }

        initViewPager()

//        favUser = FavoriteUser()

        Log.d("TAG ", userId + usernameFav + avatar)

        favUserViewModel = obtainViewModel(this@UserDetailActivity)

        binding.ivFavorite.setOnClickListener {
//            favUser.let { fav ->
//                fav?.userId = userId
//                fav?.username = usernameFav
//                fav?.avatar = avatar
//            }
            if (binding.ivFavorite.tag.toString() == "unlike") {
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
                favUserViewModel.insert(favUser as FavoriteUser)
                binding.ivFavorite.tag = "like"
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                favUserViewModel.delete(favUser as FavoriteUser)
                binding.ivFavorite.tag = "unlike"
            }
        }
    }

    private fun updateUserData(data: UserItemDetail) {
        Glide.with(this)
            .load(data.avatar_url)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.noimg)
            )
            .into(binding.ivUser)

        binding.tvUsername.text = data.login

        if (data.name == null) {
            binding.tvName.text = resources.getString(R.string.no_name)
        } else {
            binding.tvName.text = data.name
        }

        binding.tvRepository.text =
            resources.getQuantityString(
                R.plurals.numberOfRepo,
                data.public_repos,
                data.public_repos
            )

        if (data.location == null) {
            binding.tvLocation.text = resources.getString(R.string.no_location)
        } else {
            binding.tvLocation.text = data.location
        }

        if (data.company == null) {
            binding.tvCompany.text = resources.getString(R.string.no_company)
        } else {
            binding.tvCompany.text = data.company
        }

        binding.tvFollow.text =
            resources.getString(R.string.no_follow, data.following, data.followers)

//        userId = data.id.toString()
//        usernameFav = data.login
//        avatar = data.avatar_url

        favUser = FavoriteUser(data.id.toString(), data.login, data.avatar_url)

        checkFav(data.id.toString())
    }

    private fun checkFav(userId: String) {
        binding.ivFavorite.visibility = View.VISIBLE
        favUserViewModel.checkFavorite(userId).observe(this, { check ->
            if (check) {
                binding.ivFavorite.tag = "like"
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.ivFavorite.tag = "unlike"
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
        })
    }

    private fun initViewPager() {
        val pages = listOf(
            FollowListFragment.newInstance("following", username.orEmpty()),
            FollowListFragment.newInstance("followers", username.orEmpty())
        )

        val titles = listOf(
            resources.getString(R.string.following), resources.getString(R.string.followers)
        )

        binding.vpFollow.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.vpFollow.adapter = PagerAdapter(pages, supportFragmentManager, lifecycle)
        binding.vpFollow.offscreenPageLimit = 2
        binding.vpFollow.isUserInputEnabled = true

        TabLayoutMediator(binding.tabLayout, binding.vpFollow) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBarDetail.visibility = View.VISIBLE
        } else {
            binding.progressBarDetail.visibility = View.GONE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavUserInsDelViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavUserInsDelViewModel::class.java)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}