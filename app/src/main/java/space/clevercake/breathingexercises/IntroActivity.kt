package space.clevercake.breathingexercises
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager

class IntroActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var dotsLayout: LinearLayout
    private val layouts = intArrayOf(//мои слайды
        R.layout.intro_slide1,
        R.layout.intro_slide2,
        R.layout.intro_slide3
    )

    // Имена для хранения настроек SharedPreferences
    private val PREF_NAME = "IntroPrefs"
    private val PREF_FIRST_LAUNCH = "isFirstTimeLaunch"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Проверка, запускается ли приложение впервые
        if (isFirstTimeLaunch()) {
            // Если приложение запущено впервые, показываем презентацию
            setContentView(R.layout.activity_intro)
            viewPager = findViewById(R.id.viewPager)
            dotsLayout = findViewById(R.id.dotsLayout)
            pagerAdapter = PagerAdapter()
            viewPager.adapter = pagerAdapter

            addDotsIndicator(0)

            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    // Ничего не делаем здесь
                }

                override fun onPageSelected(position: Int) {
                    addDotsIndicator(position)

                    // Проверяем, является ли текущая страница последней
                    if (position == layouts.size - 1) {
                        // Устанавливаем флаг первого запуска в false
                        setFirstTimeLaunch(false)

                        // Запускаем Menu
                        val buttonNexttoMenu = findViewById<Button>(R.id.button4)
                        buttonNexttoMenu.setOnClickListener {
                            try {
                                startActivity(Intent(this@IntroActivity, Menu::class.java))
                                finish()
                            } catch (e: Exception) {
                            }
                        }
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {
                    // Ничего не делаем здесь
                }
            })
        } else {
            // Если приложение уже запускалось, переходим к основному экрану
            navigateToMainActivity()
        }
    }

    private fun addDotsIndicator(position: Int) {
        val dots: Array<ImageView?> = arrayOfNulls(layouts.size)
        dotsLayout.removeAllViews()

        for (i in dots.indices) {
            dots[i] = ImageView(this)
            dots[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.dot_inactive
                )
            )
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(8, 0, 8, 0)
            }
            dotsLayout.addView(dots[i], params)
        }

        if (dots.isNotEmpty()) {
            dots[position]?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.dot_active
                )
            )
        }
    }

    private fun isFirstTimeLaunch(): Boolean {
        // Проверка, было ли приложение уже запущено ранее
        val preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        return preferences.getBoolean(PREF_FIRST_LAUNCH, true)
    }

    private fun setFirstTimeLaunch(isFirstTime: Boolean) {
        // Установка флага первого запуска в SharedPreferences
        val preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean(PREF_FIRST_LAUNCH, isFirstTime)
        editor.apply()
    }

    private fun navigateToMainActivity() {
        // Переход к главному экрану (MainActivity или Menu, в зависимости от вашей логики)
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
        finish()
    }

    private inner class PagerAdapter : androidx.viewpager.widget.PagerAdapter() {
        // Адаптер для управления слайдами
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            // Создание и отображение слайда
            val inflater =
                getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(layouts[position], container, false)
            container.addView(view)
            return view
        }

        override fun getCount(): Int = layouts.size

        override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            // Уничтожение слайда при его удалении
            container.removeView(obj as View)
        }
    }
}