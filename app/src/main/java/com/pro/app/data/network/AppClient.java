package com.pro.app.data.network;

import android.util.Log;

import com.appizona.yehiahd.fastsave.FastSave;
import com.pro.app.BuildConfig;
import com.pro.app.utils.AppUtilsKotlin;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        if (retrofit == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> {
                if (BuildConfig.DEBUG) {

                    if (AppUtilsKotlin.Companion.isJSONValid(message)) {
                        Log.i("OkHttp", AppUtilsKotlin.Companion.formatString(message));
                    } else {
                        Log.i("OkHttp", message);
                    }
                    String log = FastSave.getInstance().getString("logs", "");
                    if (log.equals("")) {
                        FastSave.getInstance().saveString("logs", message + System.getProperty("line.separator"));
                    } else {
                        FastSave.getInstance().saveString("logs", message + System.getProperty("line.separator") + log);
                    }
                }
            });

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(60, TimeUnit.SECONDS);
            httpClient.connectTimeout(60, TimeUnit.SECONDS);
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addNetworkInterceptor(logging);
            httpClient.addInterceptor(new NetworkConnectionInterceptor() {

            });

            retrofit
                    = new Retrofit.Builder()
                    .baseUrl(EndPoints.BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
