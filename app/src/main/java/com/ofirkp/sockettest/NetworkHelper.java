package com.ofirkp.sockettest;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

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

    public static void disconnect() {
        if(socket != null) {
            sendToServer("quit");
            socket.close();
            socket = null;
        }
    }

    public static void sendBroadcast(DatagramSocket socket, String data, final Context context) throws SocketException {
        DatagramPacket packet = null;
        try {
            packet = new DatagramPacket(data.getBytes(), data.length(), InetAddress.getByName("255.255.255.255"), 8888);
            socket.send(packet);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Broadcast the message over all the network interfaces
        Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();

            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue; // Don't want to broadcast to the loopback interface
            }

            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                InetAddress broadcast = interfaceAddress.getBroadcast();
                if (broadcast == null) {
                    continue;
                }

                // Send the broadcast package!
                try {
                    packet = new DatagramPacket(data.getBytes(), data.length(), broadcast, 8888);
                    socket.send(packet);
                } catch (Exception e) {
                }

            }
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
