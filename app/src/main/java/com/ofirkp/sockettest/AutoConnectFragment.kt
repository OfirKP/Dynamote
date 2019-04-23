package com.ofirkp.sockettest

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_auto_connect.*
import kotlinx.android.synthetic.main.fragment_auto_connect.view.*
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AutoConnectFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AutoConnectFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AutoConnectFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var listener: OnFragmentInteractionListener2? = null
    var bar: ProgressBar? = null
    var refresh: Button? = null
    var mBackgroundTask: AutoConnect? = null
    lateinit var textViewAutoConnect: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_auto_connect, container, false)
        bar = view.progressBar
        refresh = view.refreshBtn as Button
        textViewAutoConnect = view.autoConnectTextView

        mBackgroundTask = AutoConnect()
        mBackgroundTask!!.execute()
        view.refreshBtn.setOnClickListener{
            setupBackgroundTask()
        }
        view.gotoManualConnectBtn.setOnClickListener { listener?.onFragmentInteraction2() }
        // Inflate the layout for this fragment
        return view
    }

    fun setupBackgroundTask() {
        if(mBackgroundTask == null) {
            mBackgroundTask = AutoConnect()
            mBackgroundTask!!.execute()
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction2()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener2) {
            listener = context
        } else {
            //throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun cancelTask(){
        mBackgroundTask?.cancel(true)
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener2 {
        // TODO: Update argument type and name
        fun onFragmentInteraction2()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AutoConnectFragment()
    }

    @SuppressLint("StaticFieldLeak")
    inner class AutoConnect : AsyncTask<Unit, String, Unit>() {


        override fun onPreExecute() {
            super.onPreExecute()
            bar?.visibility = View.VISIBLE
            textViewAutoConnect.visibility = View.VISIBLE
            refresh?.isEnabled = false
        }

        override fun onProgressUpdate(vararg values: String?) {
            super.onProgressUpdate(*values)
            Toast.makeText(activity, values[0], Toast.LENGTH_LONG).show()
        }

        override fun onCancelled() {
            super.onCancelled()
            mBackgroundTask = null
        }

        override fun doInBackground(vararg params: Unit?) {
            val detailsSocket = DatagramSocket()
            val requestString = "connect".toByteArray()
            var receivePacket: DatagramPacket? = null
            for(i in 0..3){
                if(isCancelled) break
                 NetworkHelper.sendBroadcast(detailsSocket, String(requestString), context)
                publishProgress("Sending broadcast #$i")

                //Wait for a response
                 receivePacket = NetworkHelper.receiveUDPPacket(detailsSocket)

                //TODO: Validate data
                if(receivePacket != null) break
            }

            //Check if the message is correct
            if(receivePacket != null) {
                val ip = receivePacket.address?.hostAddress
                val data = String(receivePacket.data).trim { it <= ' ' }

                //Onto TCP
                val client =
                    SocketClient(InetAddress.getByName(ip), data.toInt())
                val serverResponse = client.readLine()
                Log.d("AutoConnectFragment", "Server says $serverResponse")
                NetworkHelper.setSocket(client)
            }
        }


        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            bar?.visibility = View.INVISIBLE
            textViewAutoConnect.visibility = View.INVISIBLE
            if(NetworkHelper.getSocket() == null)
            {
                refresh?.isEnabled = true
            }
            else
            {
               startActivity(Intent(context, MainActivity::class.java))
            }
        }

    }

    fun getDetails(context: Context){

    }
}
