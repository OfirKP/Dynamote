package com.ofirkp.sockettest;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkHelper {


    public static void sendBroadcast(DatagramSocket socket, String data, final Context context)
    {
        DatagramPacket packet = null;
        try {
            packet = new DatagramPacket(data.getBytes(), data.length(), InetAddress.getByName("255.255.255.255"), 8888);
            socket.send(packet);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText( context, ">>> Request packet sent to: 255.255.255.255 (DEFAULT)", Toast.LENGTH_LONG);
                }
            });

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static DatagramPacket receiveUDPPacket(DatagramSocket socket)
    {
        byte[] recvBuf = new byte[15000];
        DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
        try {
            socket.receive(packet);
            return packet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
