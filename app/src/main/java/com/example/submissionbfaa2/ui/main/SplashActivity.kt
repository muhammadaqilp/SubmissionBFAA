package com.example.submissionbfaa2.ui.main

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.submissionbfaa2.databinding.ActivitySplashBinding
import com.example.submissionbfaa2.helper.ThemeViewModelFactory
import com.example.submissionbfaa2.preferences.SwitchThemeViewModel
import com.example.submissionbfaa2.preferences.ThemePreferences
import com.example.submissionbfaa2.ui.settings.dataStore
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root

        supportActionBar?.hide()

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        val pref = ThemePreferences.getInstance(dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref)).get(
            SwitchThemeViewModel::class.java
        )

        themeViewModel.getThemeSetting().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                setContentView(view)
            })

        activityScope.launch {
            delay(2000)
            runOnUiThread {
                mainActivity()
            }
        }
    }

    private fun mainActivity() {
        MainActivity.start(this)
        finish()
    }

    override fun onStop() {
        super.onStop()
        activityScope.coroutineContext.cancelChildren()
    }

}