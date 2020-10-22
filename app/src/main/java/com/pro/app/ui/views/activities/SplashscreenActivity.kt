package com.pro.app.ui.views.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.pro.app.R
import com.pro.app.ui.viewmodels.SplashViewModel
import kotlinx.android.synthetic.main.layout_loader.*

class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        }

        rlLoader.visibility= View.VISIBLE

        val splashViewModel = SplashViewModel()
        splashViewModel.liveData.observe(this, Observer {
            when (it) {
                is SplashViewModel.SplashState.MainActivity -> {

                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }
            }
        })
    }
}