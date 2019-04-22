package com.ofirkp.sockettest

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_connect.*
import android.support.v4.view.ViewPager.OnPageChangeListener



class ConnectActivity : AppCompatActivity(), ManualConnectFragment.OnFragmentInteractionListener, AutoConnectFragment.OnFragmentInteractionListener2{

    private lateinit var pagerAdapter: ConnectPagerAdapter
    private lateinit var autoConnectFragment: AutoConnectFragment
    private lateinit var manualConnectFragment: ManualConnectFragment

    override fun onFragmentInteraction2() {
        autoConnectFragment.cancelTask()
        container_login.currentItem = 1
    }

    override fun onFragmentInteraction() {
        container_login.currentItem = 0
        autoConnectFragment.setupBackgroundTask()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        autoConnectFragment = AutoConnectFragment.newInstance()
        manualConnectFragment = ManualConnectFragment.newInstance()

        pagerAdapter = ConnectPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(autoConnectFragment)
        pagerAdapter.addFragment(manualConnectFragment)
        container_login.adapter = pagerAdapter
//        container_login.addOnPageChangeListener(object : OnPageChangeListener {
//            override fun onPageScrollStateChanged(state: Int) {}
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
//
//            override fun onPageSelected(position: Int) {
//                // Check if this is the page you want.
//            }
//        })
    }

}
