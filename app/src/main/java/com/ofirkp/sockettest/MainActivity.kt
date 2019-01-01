package com.ofirkp.sockettest

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

// Test for GitHub

class MainActivity : AppCompatActivity() {

    internal inner class MyThread(caption: String) : Thread(caption) {

        fun showToast(toast: String) {
            runOnUiThread { Toast.makeText(this@MainActivity, toast, Toast.LENGTH_LONG).show() }
        }

        override fun run() {
            var c: DatagramSocket? = null
            val sendData = "Hello!".toByteArray()

            try {
                c = DatagramSocket()
                val sendPacket = DatagramPacket(sendData, sendData.size, InetAddress.getByName("255.255.255.255"), 8888)
                c.send(sendPacket)
                showToast(javaClass.name + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)")

                //Wait for a response
                val recvBuf = ByteArray(15000)
                val receivePacket = DatagramPacket(recvBuf, recvBuf.size)
                c.receive(receivePacket)

                //We have a response
                showToast(javaClass.name + ">>> Broadcast response from server: " + receivePacket.address.hostAddress)

                //Check if the message is correct
                val ip = receivePacket.address.hostAddress
                val data = String(receivePacket.data).trim { it <= ' ' }
                runOnUiThread {
                    textView.text = data
                    textIP.setText(ip)
                }

            } catch (e: Exception) {
                c?.close()
            }

            c?.close()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var outStream: DataOutputStream? = null

        val thread = MyThread("t1")
        thread.start()

        connectBtn.setOnClickListener {
            Thread {
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
            }.start()
        }
        button.setOnClickListener{
            Thread {
                outStream?.writeUTF(editText.text.toString())
                outStream?.flush()
                runOnUiThread() {
                    Toast.makeText(this, "Sent ${editText.text}!", Toast.LENGTH_LONG).show()
                }
            }.start()
        }
    }

}
