package com.pro.app.ui.view.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.appizona.yehiahd.fastsave.FastSave
import com.pro.app.ui.views.activities.MainActivity
import com.pro.app.R
import com.pro.app.ui.viewmodels.SplashViewModel
import com.pro.app.ui.views.activities.LoginActivity
import com.pro.app.utils.Constants

class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_splashscreen)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        }

        val token = FastSave.getInstance().getString(Constants.SP_USER_TOKEN, "")

        val splashViewModel = SplashViewModel()
        splashViewModel.liveData.observe(this, Observer {
            when (it) {
                is SplashViewModel.SplashState.MainActivity -> {

                    if (token != null && token.isNotEmpty()) {
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }

                }
            }
        })
    }
}