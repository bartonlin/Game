package com.example.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var btn_submit: ImageButton? = null
    var show_answer: Button? = null
    var new_game: Button? = null

    var flag: Boolean? = false

    var img_x: Int? = 0
    var img_y: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        img_x = Random.nextInt(displayMetrics.widthPixels / 2)
        img_y = Random.nextInt(displayMetrics.heightPixels / 2)

        btn_submit = findViewById<ImageButton>(R.id.imgbtn)
        show_answer = findViewById<Button>(R.id.showbtn)
        new_game = findViewById<Button>(R.id.resetbtn)

        val params = RelativeLayout.LayoutParams(
            Math.round(100 * displayMetrics.density),
            Math.round(100 * displayMetrics.density)
        )
        params.leftMargin = img_x as Int
        params.topMargin = img_y as Int
        btn_submit!!.layoutParams = params

        btn_submit?.setOnClickListener {
            (btn_submit as ImageButton).setImageResource(R.drawable.test)

            flag = true
            (show_answer as Button).text = "隱藏月月"
        }

        show_answer?.setOnClickListener {
            if (flag == false) {
                (btn_submit as ImageButton).setImageResource(R.drawable.test)
                (show_answer as Button).text = "隱藏月月"
                flag = true
            } else {
                (btn_submit as ImageButton).setImageResource(R.drawable.init_button)
                (show_answer as Button).text = "顯示月月"
                flag = false
            }
        }


        new_game?.setOnClickListener {
            img_x = Random.nextInt(displayMetrics.widthPixels / 2)
            img_y = Random.nextInt(displayMetrics.heightPixels / 2)

            params.leftMargin = img_x as Int
            params.topMargin = img_y as Int
            btn_submit!!.layoutParams = params

            flag = false
            (btn_submit as ImageButton).setImageResource(R.drawable.init_button)
            (show_answer as Button).text = "顯示月月"
        }

    }
}
