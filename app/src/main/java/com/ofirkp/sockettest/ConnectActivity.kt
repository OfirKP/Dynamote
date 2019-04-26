package com.ofirkp.sockettest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_connect.*
import android.widget.Toast


class ConnectActivity : AppCompatActivity(), ManualConnectFragment.OnFragmentInteractionListener,
    AutoConnectFragment.OnFragmentInteractionListener2,
    RemotesFragment.OnListFragmentInteractionListener{

    private lateinit var pagerAdapter: MyPagerAdapter
    private lateinit var autoConnectFragment: AutoConnectFragment
    private lateinit var manualConnectFragment: ManualConnectFragment
    private lateinit var recyclerFragment: RemotesFragment

    override fun onFragmentInteraction2() {
        container_login.setCurrentItem(1, true)
        autoConnectFragment.cancelTask()
    }

    override fun onFragmentInteraction() {
        container_login.setCurrentItem(0, true)
        autoConnectFragment.setupBackgroundTask()
    }

    override fun onListFragmentInteraction(item: Remote?) {
        Toast.makeText(this, "Item $item is clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        autoConnectFragment = AutoConnectFragment.newInstance()
        manualConnectFragment = ManualConnectFragment.newInstance()
        recyclerFragment = RemotesFragment.newInstance(2)

        pagerAdapter = MyPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(autoConnectFragment, "Auto Connect")
        pagerAdapter.addFragment(manualConnectFragment, "Manual Connect")
        container_login.setPagingEnabled(false)
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
