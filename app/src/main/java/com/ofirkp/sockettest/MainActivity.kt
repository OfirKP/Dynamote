package com.ofirkp.sockettest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_connect.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.DataOutputStream
import java.net.DatagramSocket
import java.net.InetAddress

// Test 00:48 2\2\19 for GitHub

class MainActivity : AppCompatActivity(), RemotesFragment.OnListFragmentInteractionListener {

    fun changePage(viewPager: ViewPager, position: Int)
    {
        Handler().post {
            remotes_appbar.setExpanded(true, true)
            viewPager.setCurrentItem(position, true)
        }
    }

    override fun onListFragmentInteraction(item: Remote?) {
        when(item?.name){
            //"Photoshop" -> startActivity(Intent(this, PhotoshopActivity::class.java))
            "Photoshop" -> changePage(container_remotes, pagerAdapter.getPositionByTitle("Photoshop"))
            "Word" -> changePage(container_remotes, pagerAdapter.getPositionByTitle("Word"))
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
        pagerAdapter.addFragment(PhotoshopFragment.newInstance(), "Photoshop")
        pagerAdapter.addFragment(WordFragment.newInstance(), "Word")

        container_remotes.adapter = pagerAdapter
        remotesTabLayout.setupWithViewPager(container_remotes)

        if(NetworkHelper.getSocket() != null)
        Thread {
            var newProgram = ""
            var message = ""
            lateinit var list: List<String>

            while(true)
            {
                message = NetworkHelper.getSocket().readLine(0)
                list = message.split(" ")
                if(list.size >= 2 && list[0] == "switch" && list[1] != "None")
                {
                    newProgram = message.substring(list[0].length + 1)
                    runOnUiThread {
                        val snackBar = Snackbar.make(main_layout, "Would" +
                                " you like to switch to $newProgram?", 5000)
                        val snackView = snackBar.view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
                        snackView.maxLines = 5
                        snackBar.setAction("Switch", ButtonChangeFragmentListener(
                            when(newProgram) {
                                "Word" -> "Word"
                                "Adobe Photoshop" -> "Photoshop"
                                else -> "All Remotes"
                            }
                        ))
                        snackBar.show()
                    }
                }
            }
        }.start()
    }


    fun disconnect()
    {
        Thread {
            NetworkHelper.disconnect()
        }.start()
        val intent = Intent(this, ConnectActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    inner class ButtonChangeFragmentListener(name: String): View.OnClickListener{
        val title = name
        override fun onClick(v: View?) {
            //container_remotes.setCurrentItem(pagerAdapter.getPositionByTitle(title), true)
            changePage(container_remotes, pagerAdapter.getPositionByTitle(title))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        if(id == R.id.action_disconnect)
            disconnect()
        return super.onOptionsItemSelected(item)
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

