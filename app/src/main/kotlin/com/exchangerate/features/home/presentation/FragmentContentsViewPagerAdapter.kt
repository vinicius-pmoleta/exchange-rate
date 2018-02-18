package com.exchangerate.features.home.presentation

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

typealias Position = Int
typealias FragmentId = Int

class FragmentContentsViewPagerAdapter(fragmentManager: FragmentManager?) : FragmentStatePagerAdapter(fragmentManager) {

    val contents = HashMap<FragmentId, Pair<Position, Fragment>>()

    override fun getItem(position: Int): Fragment {
        contents.forEach { content ->
            if (content.value.first == position) {
                return content.value.second
            }
        }
        return Fragment()
    }

    override fun getCount(): Int {
        return contents.size
    }
}