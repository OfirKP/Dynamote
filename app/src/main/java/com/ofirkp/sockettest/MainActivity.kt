package com.ofirkp.sockettest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_connect.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.DataOutputStream
import java.net.DatagramSocket
import java.net.InetAddress

// Test 00:48 2\2\19 for GitHub

class MainActivity : AppCompatActivity(), RemotesFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: Remote?) {
        when(item?.name){
            "Photoshop" -> startActivity(Intent(this, PhotoshopActivity::class.java))
            "Word" -> startActivity(Intent(this, WordActivity::class.java))
            else -> startActivity(Intent(this, PhotoshopActivity::class.java))
        }
    }

    private lateinit var pagerAdapter: MyPagerAdapter
    private lateinit var remotesFragment: RemotesFragment

    inline fun showToast(toast: String) {
        runOnUiThread { Toast.makeText(this@MainActivity, toast, Toast.LENGTH_SHORT).show() }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val outStream: DataOutputStream? = null
        var client: SocketClient? = null

        remotesFragment = RemotesFragment.newInstance(2)

        pagerAdapter = MyPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(remotesFragment, "All Remotes")
        pagerAdapter.addFragment(ManualConnectFragment.newInstance(), "Photoshop")
        pagerAdapter.addFragment(ManualConnectFragment.newInstance(), "Word")

        container_remotes.adapter = pagerAdapter
        remotesTabLayout.setupWithViewPager(container_remotes)
    }

        /*wordBtn.setOnClickListener{
            startActivity(Intent(this, WordActivity::class.java))
        }
        psBtn.setOnClickListener{
            startActivity(Intent(this, PhotoshopActivity::class.java))
        }
        getDetailsBtn.setOnClickListener{
            val thread = MyThread("t1")
            thread.start()
        }
        disconnectBtn.setOnClickListener {
            NetworkHelper.sendToServer("quit")
            client?.close()
        }
        manualConnectBtn.setOnClickListener {
            Thread {
                client = SocketClient(InetAddress.getByName(textIP.text.toString()), textPort.text.toString().toInt())
                val serverResponse = client?.readLine()
                showToast("Server says $serverResponse")
                NetworkHelper.setSocket(client)

            }.start()
        }
        button.setOnClickListener{
            Thread {
                NetworkHelper.sendToServer(editText.text.toString())
                showToast("Sent ${editText.text}!")
                /*outStream?.writeUTF(editText.text.toString())
                outStream?.flush()
                runOnUiThread() {
                    Toast.makeText(this, "Sent ${editText.text}!", Toast.LENGTH_LONG).show()
                }
                */
            }.start()
        }

    fun getDetails(){
        var c: DatagramSocket? = null
        val sendData = "connect".toByteArray()

        try {
            c = DatagramSocket()
            NetworkHelper.sendBroadcast(c, "connect", this@MainActivity);

            //Wait for a response
            val receivePacket = NetworkHelper.receiveUDPPacket(c);

            //We have a response
            showToast(javaClass.name + ">>> Broadcast response from server: " + receivePacket.address.hostAddress)

            //Check if the message is correct
            val ip = receivePacket.address.hostAddress
            val data = String(receivePacket.data).trim { it <= ' ' }
            runOnUiThread {
                //showToast("Got computer details automatically")
                textView.text = data
                textIP.setText(ip)
                textPort.setText(data)
            }

        } catch (e: Exception) {
            c?.close()
        }

        c?.close()
    }

    internal inner class MyThread(caption: String) : Thread(caption) {
        // This is a test for GitHub


        override fun run() {
            getDetails()
            showToast("Auto Connected")
        }
    }

    */


}

