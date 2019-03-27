package com.ofirkp.sockettest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.DataInputStream
import java.net.Socket
import java.io.DataInputStream.readUTF
import java.io.DataOutputStream
import java.io.PrintWriter
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

// Test 00:48 2\2\19 for GitHub

class MainActivity : AppCompatActivity() {
    inline fun showToast(toast: String) {
        runOnUiThread { Toast.makeText(this@MainActivity, toast, Toast.LENGTH_LONG).show() }
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val outStream: DataOutputStream? = null
        var client: SocketClient? = null

        wordBtn.setOnClickListener{
            startActivity(Intent(this, WordActivity::class.java))
        }
        getDetailsBtn.setOnClickListener{
            val thread = MyThread("t1")
            thread.start()
        }
        connectBtn.setOnClickListener {
            Thread {
                client = SocketClient(InetAddress.getByName(textIP.text.toString()), textPort.text.toString().toInt())
                val serverResponse = client?.readLine()
                showToast("Server says $serverResponse")
                NetworkHelper.setSocket(client)
                disconnectBtn.setOnClickListener {
                    client?.close()
                }
                /*
                val client: Socket = Socket(textIP.text.toString(), textPort.text.toString().toInt())
                val inFromServer = client.getInputStream()
                val input = DataInputStream(inFromServer)
                var textIn = ""
                var c: Int
                outStream = DataOutputStream(client.getOutputStream())
                val out = PrintWriter(outStream)
                while (true) {
                    c = input.read()
                    if (c != -1 && c != 13 && c != 10)
                        textIn += c.toChar()
                    else
                        break
                }

                runOnUiThread {
                    Toast.makeText(this, "Server says " + textIn, Toast.LENGTH_LONG).show()
                }

                disconnectBtn.setOnClickListener {
                    client.close()
                }
                */
            }.start()
        }
        button.setOnClickListener{
            Thread {
                client?.println(editText.text.toString())
                showToast("Sent ${editText.text}!")
                /*outStream?.writeUTF(editText.text.toString())
                outStream?.flush()
                runOnUiThread() {
                    Toast.makeText(this, "Sent ${editText.text}!", Toast.LENGTH_LONG).show()
                }
                */
            }.start()
        }
    }

}