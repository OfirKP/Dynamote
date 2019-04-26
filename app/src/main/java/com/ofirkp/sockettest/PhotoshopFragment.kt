package com.ofirkp.sockettest


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_photoshop.*
import kotlinx.android.synthetic.main.fragment_photoshop.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [PhotoshopFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PhotoshopFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_photoshop, container, false)

       /* view.colorPicker.setOnTouchListener { v, event ->
            //v.parent.parent.parent.requestDisallowInterceptTouchEvent(true)
            Toast.makeText(context, "Touching", Toast.LENGTH_SHORT).show()
            true
        }
        */
        view.colorPicker.setOnlyUpdateOnTouchEventUp(true)
        view.colorPicker.subscribe { color, fromUser, shouldPropagate ->
            val hex = colorHex(color)
            view.textView2.setText("#$hex")
            NetworkHelper.sendToServer("color $hex")
        }
        view.moveToolBtn.setOnClickListener{
            NetworkHelper.sendToServer("tool moveTool")
        }
        view.brushToolBtn.setOnClickListener{
            NetworkHelper.sendToServer("tool paintbrushTool")
        }
        view.cloneStampToolBtn.setOnClickListener{
            NetworkHelper.sendToServer("tool cloneStampTool")
        }

        return view
    }

    private fun colorHex(color: Int): String {
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        return String.format(Locale.getDefault(), "%02X%02X%02X", r, g, b)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment PhotoshopFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            PhotoshopFragment()
    }
}
