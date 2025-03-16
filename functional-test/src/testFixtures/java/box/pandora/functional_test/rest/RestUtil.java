package box.pandora.functional_test.rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.List;

public final class RestUtil {

    private RestUtil() {
    }

    public static okhttp3.OkHttpClient createOkHttpClient() {
        return new OkHttpClient().newBuilder()
                .addInterceptor(new RequestInterceptor())
                .addNetworkInterceptor(new HttpLoggingInterceptor(new HttpLogger(true, List.of()))
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

}
