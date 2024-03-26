package space.clevercake.breathingexercises

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Practic1  : AppCompatActivity() {
    private var textCount = 0
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.practic_for_video)

//видео
            val videoView1 = findViewById<VideoView>(R.id.videoView)
            videoView1.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.dog01))
            //Зациклили видео
            videoView1.setOnPreparedListener { mediaPlayer -> mediaPlayer.isLooping = true }
            videoView1.start()


            val videoView = findViewById<VideoView>(R.id.videoView)

            val buttonStart = findViewById<Button>(R.id.button) //кнопка старт

            var stopbuttonlisten: Boolean = false
            val text = findViewById<TextView>(R.id.textView) as TextView
            text.text = ""
            var currentJob: Job? = null //переменная, которая хранит ссылку на текущий запущенный поток
            //КНОПКА ОСТАНОВКИ
            val buttonStop = findViewById<Button>(R.id.button2) //кнопка стon
            buttonStop.isEnabled = false
            buttonStop.setOnClickListener {

                try {
                    currentJob?.cancel()
                    stopbuttonlisten = true
                    text.text = ""
                    videoView.stopPlayback()
                    videoView1.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.dog01))
                    //Зациклили видео
                    videoView1.setOnPreparedListener { mediaPlayer -> mediaPlayer.isLooping = true }
                    videoView1.start()
                    // Отключаем кнопку остановки и включаем кнопку запуска
                    buttonStop.isEnabled = false
                    buttonStart.isEnabled = true
                } catch (e: Exception) {
                } //конец конструкции чтобы не вылетало
            }

            //КНОПКА ЗАПУСКА
            buttonStart.setOnClickListener {
                stopbuttonlisten = false
                currentJob?.cancel()
                videoView1.stopPlayback()
                // более универсальный вариант
                videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.dog1_new))
                videoView.setOnPreparedListener { mediaPlayer -> mediaPlayer.isLooping = true }

                videoView.start()

                // Если предыдущий процесс был остановлен, сбрасываем флаг

                // Включаем кнопку остановки и отключаем кнопку запуска
                buttonStop.isEnabled = true
                buttonStart.isEnabled = false

                currentJob =
                    lifecycleScope.launch {//Использование lifecycleScope предпочтительнее, чем использование GlobalScope
                        while (!stopbuttonlisten) {
                            runOnUiThread {
                                text.setText(R.string.inhale)
                            }
                            delay(4050)
                            if (stopbuttonlisten) {

                                break
                            }
                            runOnUiThread {
                                text.setText(R.string.delay)
                            }
                            delay(4050)
                            if (stopbuttonlisten) {
                                break
                            }
                            runOnUiThread {
                                text.setText(R.string.exhale)
                            }
                            delay(4050)
                            if (stopbuttonlisten) {
                                break
                            }
                            runOnUiThread {
                                text.setText(R.string.delay)
                            }
                            delay(4150)
                            if (stopbuttonlisten) {
                                break
                            }

                            // После каждой итерации увеличивайте счетчик
                            textCount++
                           // Проверьте, достигло ли количество воспроизведений 6
                            if (textCount == 6) {
                                // Поздравительное сообщение
                                videoView.stopPlayback()
                                videoView1.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.dog01))
                                //Зациклили видео
                                videoView1.setOnPreparedListener { mediaPlayer -> mediaPlayer.isLooping = true }
                                videoView1.start()
                                text.setText("Тренировка завершина :)")
                                buttonStop.isEnabled = false
                                buttonStart.isEnabled = true
                                textCount = 0
                                break
                            }
                        }
                    }

            }
            //КНОПКА НАЗАД
            val buttonBack = findViewById<View>(R.id.button5) //кнопка старт
            buttonBack.setOnClickListener(){
                try {
                    startActivity(Intent(this@Practic1, Menu::class.java))
                    finish()
                } catch (e: Exception) {
                } //конец конструкции чтобы не вылетало
            }

        }
    }

//                if (stopbuttonlisten) {
//                    // Если предыдущий процесс был остановлен, сбрасываем флаг
//                    stopbuttonlisten = false// Если предыдущий процесс был остановлен, сбрасываем флаг
//
//                    GlobalScope.launch {//фоновый поток
//                        val text = findViewById<TextView>(R.id.textView) as TextView
//                        while (!stopbuttonlisten) {
//                            runOnUiThread {//для взаимодействия с интерфейсом из фонового потока
//                                text.text = "Вдох"
//                            }
//                            delay(4000)
//                            if (stopbuttonlisten) {
//                                break
//                            }
//                            runOnUiThread {//для взаимодействия с интерфейсом из фонового потока
//                                text.text = "Задержите дыхание"
//                            }
//                            delay(4000)
//                            if (stopbuttonlisten) {
//                                break
//                            }
//                            runOnUiThread {//для взаимодействия с интерфейсом из фонового потока
//                                text.text = "Выдох"
//                            }
//                            delay(4000)
//                            if (stopbuttonlisten) {
//                                break
//                            }
//                            runOnUiThread {//для взаимодействия с интерфейсом из фонового потока
//                                text.text = "Задержите дыхание"
//                            }
//                            delay(4000)
//                        }
//                    }
//                }
//
//            } catch (e: Exception) {
//            } //конец конструкции чтобы не вылетало
//        }



//     fun text(true22:Boolean){
//        val text = findViewById<TextView>(R.id.textView) as TextView
//        if(true22==true) {
//            var a =0
//            while (a<=5000){
//                if (a>=0&& a<250){
//                    text.text = "Вдох"
//                }else if (a>=250&& a<500){
//                    text.text = "Выдох"
//                }else if (a>=500&& a<750){
//                    text.text = "Вдох"
//                }else {
//                    text.text = "Выдох"
//                }
//
//                a=a+1
//            }
//
//        }else{
//            text.text = ""
//        }

//
//    fun text(true22:Boolean) {
//        runBlocking {
//            launch {
//                while (true22 != false){
//                    println("Вдох")
//                    delay(4000)
//                    println("Задержите дыхание")
//                    delay(4000)
//                    println("Выдох")
//                    delay(4000)
//                    println("Задержите дыхание")
//                    delay(4000)
//                }
//
//            }
//        }
//    }

