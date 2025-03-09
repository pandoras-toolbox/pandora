package box.pandora.payroll_test_okhttp.client;

import box.pandora.functional_test.ApiException;
import box.pandora.functional_test.rest.HttpLogger;
import box.pandora.functional_test.rest.HttpResponse;
import box.pandora.functional_test.rest.RequestInterceptor;
import io.qameta.allure.Step;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.List;

public final class OrderHttpClient {

    OrderHttpClient() {
    }

    @Step
    public static HttpResponse addEmployee(String name, String role) {
        var client = new OkHttpClient().newBuilder()
                .addInterceptor(new RequestInterceptor())
                .addNetworkInterceptor(new HttpLoggingInterceptor(new HttpLogger(true, List.of()))
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        try (var response = client.newCall(new Request.Builder()
                        .url(new HttpUrl.Builder()
                                .scheme("HTTP")
                                .host("localhost")
                                .port(8080)
                                .addPathSegment("employees")
                                .build())
                        .header("Content-Type", "application/json")
                        .post(RequestBody.create("""
                                { "name": "%s", "role": "%s" }"""
                                .formatted(name, role)
                                .getBytes()))
                        .build())
                .execute()) {
            return HttpResponse.fromOkHttpResponse(response);
        } catch (IOException e) {
            throw new ApiException("Failed to add employee with name '%s' and role '%s'"
                    .formatted(name, role), e);
        }
    }

}
