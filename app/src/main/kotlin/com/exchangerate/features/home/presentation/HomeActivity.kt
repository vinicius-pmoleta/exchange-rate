package com.exchangerate.features.home.presentation

import android.os.Bundle
import com.exchangerate.R
import com.exchangerate.core.structure.BaseActivity
import com.exchangerate.features.conversion.presentation.ConversionFragment
import com.exchangerate.features.usage.presentation.UsageFragment
import kotlinx.android.synthetic.main.home_activity.homeContent
import kotlinx.android.synthetic.main.home_activity.homeNavigation


class HomeActivity : BaseActivity() {

    private lateinit var contentsAdapter: FragmentContentsViewPagerAdapter

    override fun initializeDependencyInjector() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        setupContentHolder()
        setupNavigation()
    }

    private fun setupContentHolder() {
        contentsAdapter = FragmentContentsViewPagerAdapter(supportFragmentManager)
        contentsAdapter.contents.let {
            it.put(R.id.home_navigation_option_settings, Pair(0, UsageFragment()))
            it.put(R.id.home_navigation_option_conversion, Pair(1, ConversionFragment()))
        }

        homeContent.isSwipeEnabled = false
        homeContent.adapter = contentsAdapter
    }

    private fun setupNavigation() {
        homeNavigation.setOnNavigationItemSelectedListener({ item ->
            contentsAdapter.contents[item.itemId]?.let {
                homeContent.currentItem = it.first
                true
            }
            false
        })
    }
}