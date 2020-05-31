package com.example.game

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt
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
            (100 * displayMetrics.density).roundToInt(),
            (100 * displayMetrics.density).roundToInt()
        )
        params.leftMargin = img_x as Int
        params.topMargin = img_y as Int

        btn_submit!!.layoutParams = params

        btn_submit?.setOnClickListener {
            (btn_submit as ImageButton).setImageResource(R.drawable.test)
            (show_answer as Button).isEnabled = false

            val context = applicationContext
            val text = "找到月月了"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(context, text, duration)
            toast.show()
        }

        show_answer?.setOnClickListener {
            if (flag == false) {
                (btn_submit as ImageButton).setImageResource(R.drawable.ans)
                (show_answer as Button).text = "隱藏月月"
                flag = true
            } else {
                (btn_submit as ImageButton).setImageResource(R.drawable.init_button)
                (show_answer as Button).text = "顯示月月"
                flag = false
            }
        }

        new_game?.setOnClickListener {
            (show_answer as Button).isEnabled = true
            img_x = Random.nextInt(displayMetrics.widthPixels / 2)
            img_y = Random.nextInt(displayMetrics.heightPixels / 2)

            params.leftMargin = img_x as Int
            params.topMargin = img_y as Int
            btn_submit!!.layoutParams = params

            flag = false
            (btn_submit as ImageButton).setImageResource(R.drawable.init_button)
            (show_answer as Button).text = "顯示月月"
        }

        val rootView = window.decorView.rootView
        rootView.setOnTouchListener{ _, event ->
            when(event!!.action and MotionEvent.ACTION_MASK) {
                ACTION_DOWN -> {
                    val x = event.rawX.toInt()
                    val y = event.rawY.toInt()
                    val distance: Double // 算與目標距離多遠

                    distance = sqrt(
                        (x - img_x!! - (50 * displayMetrics.density).roundToInt()).toDouble().pow(2) + (y - img_y!! - 250 - (50 * displayMetrics.density).roundToInt()).toDouble().pow(2)
                    )

                    var virsec = 10
                    if (distance > 500) virsec = 100
                    else if (distance > 250 && distance <= 500) virsec = 300
                    else if (distance <= 250) virsec = 500

                    val vir =
                        getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    // Vibrate for 500 milliseconds
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vir.vibrate(
                            VibrationEffect.createOneShot(
                                virsec.toLong(),
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                        )
                    } else {
                        //deprecated in API 26
                        vir.vibrate(virsec.toLong())
                    }
                }
            }
            true
        }
    }
}
