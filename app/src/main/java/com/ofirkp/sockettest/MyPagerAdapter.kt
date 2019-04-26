package com.ofirkp.sockettest

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter

class MyPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    private val fragments = ArrayList<Fragment>()
    private val titles = ArrayList<String>()

    fun addFragment(f: Fragment, title: String): Unit {
        fragments.add(f)
        titles.add(title)
    }

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    fun getPositionByTitle(title: String) = titles.indexOf(title)
}