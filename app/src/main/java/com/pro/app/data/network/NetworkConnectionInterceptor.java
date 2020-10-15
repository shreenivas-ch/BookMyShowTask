package com.pro.app.data.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import com.appizona.yehiahd.fastsave.FastSave;
import com.mindorks.nybus.NYBus;
import com.pro.app.MainApplication;
import com.pro.app.data.events.NoInternetEvent;
import com.pro.app.utils.Constants;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public abstract class NetworkConnectionInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (!MainApplication.Companion.getInstance().isInternetAvailable()) {
                throw new NoConnectivityException();
            }
        }
        else
        {
            ConnectivityManager connectivityManager = (ConnectivityManager) MainApplication.Companion.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkRequest.Builder builder = new NetworkRequest.Builder();

            connectivityManager.registerNetworkCallback(
                    builder.build(),
                    new ConnectivityManager.NetworkCallback() {
                        @Override
                        public void onAvailable(Network network) {
                        }
                        @Override
                        public void onLost(Network network) {
                            NYBus.get().post(new NoInternetEvent());
                        }
                    }

            );
        }

        Request original = chain.request();
        String android_id = MainApplication.Companion.getInstance().getDeviceID();

        String token = FastSave.getInstance().getString(Constants.SP_USER_TOKEN, "");
        token = (token != null) ? token : "";

        Request request = original.newBuilder()
                .header("token", token)
                .header("os", "android")
                .header("duid", android_id)
                .header("version", "1")
                .header("app_version", BuildConfig.VERSION_NAME)
                .header("app_version_code", BuildConfig.VERSION_CODE + "")
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);
    }

    class NoConnectivityException extends IOException {

        @Override
        public String getMessage() {
            return "Network Connection exception";
        }

    }
}
