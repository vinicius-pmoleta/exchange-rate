package com.exchangerate.features.home.presentation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.exchangerate.R
import com.exchangerate.core.structure.BaseActivity
import com.exchangerate.features.conversion.presentation.ConversionFragment
import com.exchangerate.features.usage.presentation.UsageFragment
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : BaseActivity() {

    override fun initializeDependencyInjector() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        setupNavigation()
        showUsage()
    }

    private fun showUsage() {
        replaceContentWith(UsageFragment.TAG, UsageFragment())
    }

    private fun showConversion() {
        replaceContentWith(ConversionFragment.TAG, ConversionFragment())
    }

    private fun showAlerts() {}

    private fun replaceContentWith(tag: String, fragment: Fragment) {
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.homeContent, fragment, tag)
                    .commit()
        }
    }

    private fun setupNavigation() {
        homeNavigation.setOnNavigationItemSelectedListener({ item ->
            handleSelectedOption(item)
        })
    }

    private fun handleSelectedOption(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_navigation_option_settings -> showUsage()
            R.id.home_navigation_option_conversion -> showConversion()
            R.id.home_navigation_option_alerts -> showAlerts()
        }
        return true
    }

}