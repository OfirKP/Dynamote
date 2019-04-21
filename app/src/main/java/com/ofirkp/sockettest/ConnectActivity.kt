package com.ofirkp.sockettest

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_connect.*

class ConnectActivity : AppCompatActivity(){


    private lateinit var pagerAdapter: ConnectPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        pagerAdapter = ConnectPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(AutoConnectFragment.newInstance())
        pagerAdapter.addFragment(ManualConnectFragment.newInstance())
        container_login.adapter = pagerAdapter
    }

}
