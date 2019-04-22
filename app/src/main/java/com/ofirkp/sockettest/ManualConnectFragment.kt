package com.ofirkp.sockettest

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_manual_connect.*
import kotlinx.android.synthetic.main.fragment_manual_connect.view.*
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ManualConnectFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ManualConnectFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ManualConnectFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var listener: OnFragmentInteractionListener? = null
    lateinit var connectBtn: Button
    lateinit var layout: CoordinatorLayout
    lateinit var mSnackBar: Snackbar
    lateinit var ipEditText: EditText
    lateinit var portEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_manual_connect, container, false)
        connectBtn = view.manualConnectBtn
        layout = view as CoordinatorLayout
        ipEditText = view.ipEditText
        portEditText = view.portEditText
        connectBtn.setOnClickListener{
            ManualConnect().execute()
        }
        view.gotoAutoConnectBtn.setOnClickListener { listener?.onFragmentInteraction() }
        return view
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            //throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
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
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction()
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ManualConnectFragment()
    }

    @SuppressLint("StaticFieldLeak")
    inner class ManualConnect : AsyncTask<Unit, String, String>() {


        override fun onPreExecute() {
            super.onPreExecute()
            mSnackBar = Snackbar.make(layout, "Connecting...", Snackbar.LENGTH_INDEFINITE)
            mSnackBar.show()
            connectBtn.isEnabled = false
        }

        override fun onProgressUpdate(vararg values: String?) {
            super.onProgressUpdate(*values)
            Toast.makeText(activity, values[0], Toast.LENGTH_LONG).show()
        }

        override fun doInBackground(vararg params: Unit?): String? {
            val ip = ipEditText.text.toString()
            val port = portEditText.text.toString().toInt()

            //Onto TCP
            val client = SocketClient(InetAddress.getByName(ip), port)
            val serverResponse = client.readLine()
            Log.d("AutoConnectFragment", "Server says $serverResponse")
            NetworkHelper.setSocket(client)
            return serverResponse
        }


        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            mSnackBar.dismiss()
            connectBtn.isEnabled = true
            if(NetworkHelper.getSocket() == null || result?.isEmpty()!!)
            {
                Toast.makeText(activity, "Couldn't connect to server", Toast.LENGTH_LONG).show()
            }
            else
            {
                startActivity(Intent(context, MainActivity::class.java))
            }
        }

    }
}
