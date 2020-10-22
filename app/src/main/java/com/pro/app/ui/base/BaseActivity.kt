package com.pro.app.ui.base

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.appizona.yehiahd.fastsave.FastSave
import com.mindorks.nybus.NYBus
import com.mindorks.nybus.annotation.Subscribe
import com.pro.app.BuildConfig
import com.pro.app.R
import com.pro.app.data.events.AuthFailEvent
import com.pro.app.data.network.AppInteractor
import com.pro.app.ui.views.activities.SplashscreenActivity
import com.pro.app.utils.NotificationHandler
import kotlinx.android.synthetic.main.layout_progressbar.*

abstract class BaseActivity : AppCompatActivity(),
    BaseMvpView {

    protected abstract val layoutId: Int
    var interactor: AppInteractor = AppInteractor()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        }

        initialization()
        registerListeners()
    }

    protected abstract fun initialization()

    open fun registerListeners() {}

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun onError(msg: String) {

    }


    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    @Subscribe
    fun onEvent(event: AuthFailEvent) {
        FastSave.getInstance().clearSession()
        val intent = Intent(this, SplashscreenActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun checkPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED || Build.VERSION.SDK_INT < Build.VERSION_CODES.M
    }

    fun requestSpecifiedPermissions(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        NYBus.get().unregister(this)
    }

    override fun onStart() {
        super.onStart()
        NYBus.get().register(this)
    }

    override fun onResume() {
        super.onResume()
        if (BuildConfig.DEBUG) {
            NotificationHandler().generateNotificationForLogs(this@BaseActivity)
        }
    }

    override fun onPause() {
        super.onPause()
        if (BuildConfig.DEBUG) {
            val nMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nMgr.cancel(-1)
        }
    }
}
