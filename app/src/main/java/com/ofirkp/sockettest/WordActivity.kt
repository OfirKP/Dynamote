package com.ofirkp.sockettest

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_word.*
import top.defaults.colorpicker.ColorObserver
import android.graphics.drawable.ColorDrawable
import android.os.Build
import java.util.*


class WordActivity : AppCompatActivity(), ColorObserver {
    override fun onColor(color: Int, fromUser: Boolean, shouldPropagate: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word)

        underlineBtn.setOnClickListener{
            NetworkHelper.sendToServer("underline")
        }
        boldBtn.setOnClickListener{
            NetworkHelper.sendToServer("bold")
        }
        increaseSizeBtn.setOnClickListener{
            NetworkHelper.sendToServer("increase")
        }
        decreaseSizeBtn.setOnClickListener{
            NetworkHelper.sendToServer("decrease")
        }


    }

}
