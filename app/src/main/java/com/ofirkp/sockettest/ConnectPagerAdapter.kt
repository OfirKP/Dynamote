package com.ofirkp.sockettest

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class ConnectPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    private val fragments = ArrayList<Fragment>()

    fun addFragment(f: Fragment): Unit {
        fragments.add(f)
    }

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]

}