package com.example.submissionbfaa2.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionbfaa2.preferences.SwitchThemeViewModel
import com.example.submissionbfaa2.preferences.ThemePreferences
import com.example.submissionbfaa2.ui.main.MainViewModel
import java.lang.IllegalArgumentException

class ThemeViewModelFactory(private val pref: ThemePreferences) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SwitchThemeViewModel::class.java)) {
            return SwitchThemeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel Class: " + modelClass.name)
    }
}