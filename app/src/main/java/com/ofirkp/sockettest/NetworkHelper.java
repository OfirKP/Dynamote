package com.ofirkp.sockettest;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.io.IOException;
import java.net.*;

public class NetworkHelper {

    private static NetworkHelper single_instance = null;
    private static SocketClient socket = null;

    public static NetworkHelper getInstance() {
        if (single_instance == null)
            single_instance = new NetworkHelper();

        return single_instance;
    }

    public static void setSocket(SocketClient s)
    {
        socket = s;
    }

    public static SocketClient getSocket() {
        return socket;
    }

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
            socket.setSoTimeout(4000);
            socket.receive(packet);
            socket.setSoTimeout(0);
            return packet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void sendToServer(final String message)
    {
        if(socket != null) {
            Runnable runnable;
            Thread t = new Thread(){
                @Override
                public void run() {
                    socket.println(message);
                }
            };
            t.start();
        }
    }
}
