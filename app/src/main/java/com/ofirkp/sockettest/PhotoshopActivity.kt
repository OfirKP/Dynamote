package com.ofirkp.sockettest

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ofirkp.sockettest.R.id.colorPicker
import kotlinx.android.synthetic.main.activity_photoshop.*
import java.util.*

class PhotoshopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photoshop)

        colorPicker.setOnlyUpdateOnTouchEventUp(true)
        colorPicker.subscribe { color, fromUser, shouldPropagate ->
            val hex = colorHex(color)
            textView2.setText("#$hex")
            NetworkHelper.sendToServer("color $hex")
        }
        moveToolBtn.setOnClickListener{
            NetworkHelper.sendToServer("tool moveTool")
        }
        brushToolBtn.setOnClickListener{
            NetworkHelper.sendToServer("tool paintbrushTool")
        }
        cloneStampToolBtn.setOnClickListener{
            NetworkHelper.sendToServer("tool cloneStampTool")
        }
    }

    private fun colorHex(color: Int): String {
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        return String.format(Locale.getDefault(), "%02X%02X%02X", r, g, b)
    }
}
