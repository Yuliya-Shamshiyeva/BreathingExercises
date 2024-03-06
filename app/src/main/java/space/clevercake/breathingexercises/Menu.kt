package space.clevercake.breathingexercises

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        //Находим liner layout который в скроле куда добавлять контейнер 3
        val liner = findViewById<View>(R.id.container3) as LinearLayout
        //Берем наш лейаут задаем все нужные данные выбранные из него

        //Берем наш лейаут задаем все нужные данные выбранные из него

        ////////////             ПРАКТИКА1 (можно попробовать использовать интерфейсы)

        val view1: View = layoutInflater.inflate(
            R.layout.practicformformenu,
            null
        ) //добавляет весь контейнер со всеми данными

        val img1 = view1.findViewById<View>(R.id.imagepracties) as ImageView
        val textTitle1 = view1.findViewById<View>(R.id.title) as TextView
        val textDescription1 = view1.findViewById<View>(R.id.textdescription) as TextView
        val buttonStart1 = view1.findViewById<Button>(R.id.button3) as Button
        img1.setImageResource(R.drawable.practic1)
        textTitle1.setText(R.string.textTitle1)
        textDescription1.setText(R.string.textDescription1)
        //добавляем элементы в линер лейаут контейнер 3
        liner.addView(view1)
        buttonStart1.setOnClickListener(){
            try {
                startActivity(Intent(this@Menu, Practic1::class.java))
                finish()
            } catch (e: Exception) {
            } //конец конструкции чтобы не вылетало
        }
        ////////////              ПРАКТИКА2

        //Берем наш лейаут задаем все нужные данные выбранные из него
        val view2: View = layoutInflater.inflate(
            R.layout.practicformformenu,
            null
        ) //добавляет весь контейнер со всеми данными
        val img2 = view2.findViewById<View>(R.id.imagepracties) as ImageView
        val textTitle2 = view2.findViewById<View>(R.id.title) as TextView
        val textDescription2 = view2.findViewById<View>(R.id.textdescription) as TextView
        val buttonStart2 = view2.findViewById<Button>(R.id.button3) as Button
        img2.setImageResource(R.drawable.practic2)
        textTitle2.setText(R.string.textTitle2)
        textDescription2.setText(R.string.textDescription2)
        //добавляем элементы в линер лейаут контейнер 3
        liner.addView(view2)
        buttonStart2.setOnClickListener(){
            try {
                startActivity(Intent(this@Menu, Practic2::class.java))
                finish()
            } catch (e: Exception) {
            } //конец конструкции чтобы не вылетало
        }
    }
    private var back_pressed: Long = 0

    override fun onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {

            super.onBackPressed()
            finishAffinity()//Завершит полностью
        } else Toast.makeText(
            baseContext, R.string.backsms,
            Toast.LENGTH_SHORT
        ).show()
        back_pressed = System.currentTimeMillis()
    }
}
