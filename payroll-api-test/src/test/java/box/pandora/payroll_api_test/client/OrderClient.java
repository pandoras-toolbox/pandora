package box.pandora.payroll_api_test.client;

import box.pandora.payroll_api_test.core.ApiException;
import box.pandora.payroll_api_test.core.HttpResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;

public final class OrderClient {

    OrderClient() {
    }

    public static HttpResponse addEmployee(String name, String role) {
        var client = new OkHttpClient();
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
