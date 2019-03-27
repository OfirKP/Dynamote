package com.ofirkp.sockettest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_word.*

class WordActivity : AppCompatActivity() {

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
