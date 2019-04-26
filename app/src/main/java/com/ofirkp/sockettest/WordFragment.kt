package com.ofirkp.sockettest


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_word.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [WordFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class WordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_word, container, false)

        view.underlineBtn.setOnClickListener{
            NetworkHelper.sendToServer("underline")
        }
        view.boldBtn.setOnClickListener{
            NetworkHelper.sendToServer("bold")
        }
        view.increaseSizeBtn.setOnClickListener{
            NetworkHelper.sendToServer("increase")
        }
        view.decreaseSizeBtn.setOnClickListener{
            NetworkHelper.sendToServer("decrease")
        }


        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment WordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = WordFragment()
    }
}
