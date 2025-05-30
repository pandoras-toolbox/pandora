package box.pandora.payroll_test_okhttp.client;

import box.pandora.functional_test.ApiException;
import box.pandora.functional_test.rest.HttpResponse;
import box.pandora.functional_test.rest.RestUtil;
import io.qameta.allure.Step;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public final class OrderClient {

    private OrderClient() {
    }

    @Step
    public static HttpResponse getEmployees() {
        try (var response = RestUtil.createOkHttpClient().newCall(new Request.Builder()
                        .url(new HttpUrl.Builder()
                                .scheme("HTTP")
                                .host("localhost")
                                .port(8080)
                                .addPathSegment("employees")
                                .build())
                        .header("Content-Type", "application/json")
                        .get()
                        .build())
                .execute()) {
            return HttpResponse.fromOkHttpResponse(response);
        } catch (IOException e) {
            throw new ApiException("Failed to get employees", e);
        }
    }

    @Step
    public static HttpResponse getEmployee(UUID id) {
        try (var response = RestUtil.createOkHttpClient().newCall(new Request.Builder()
                        .url(new HttpUrl.Builder()
                                .scheme("HTTP")
                                .host("localhost")
                                .port(8080)
                                .addPathSegment("employees")
                                .addPathSegment(String.valueOf(id))
                                .build())
                        .header("Content-Type", "application/json")
                        .get()
                        .build())
                .execute()) {
            return HttpResponse.fromOkHttpResponse(response);
        } catch (IOException e) {
            throw new ApiException("Failed to get employee by ID '%s'"
                    .formatted(id), e);
        }
    }

    @Step
    public static HttpResponse addEmployee(String name, String role, UUID id) {
        String json;
        if (id == null) {
            json = """
                    { "name": "%s", "role": "%s" }""".formatted(name, role);
        } else {
            json = """
                    { "id": "%s", "name": "%s", "role": "%s" }""".formatted(id, name, role);
        }
        try (var response = RestUtil.createOkHttpClient().newCall(new Request.Builder()
                        .url(new HttpUrl.Builder()
                                .scheme("HTTP")
                                .host("localhost")
                                .port(8080)
                                .addPathSegment("employees")
                                .build())
                        .header("Content-Type", "application/json")
                        .post(RequestBody.create(json.getBytes(StandardCharsets.UTF_8)))
                        .build())
                .execute()) {
            return HttpResponse.fromOkHttpResponse(response);
        } catch (IOException e) {
            throw new ApiException("Failed to add employee with name '%s' and role '%s'"
                    .formatted(name, role), e);
        }
    }

    @Step
    public static HttpResponse deleteEmployee(UUID id) {
        try (var response = RestUtil.createOkHttpClient().newCall(new Request.Builder()
                        .url(new HttpUrl.Builder()
                                .scheme("HTTP")
                                .host("localhost")
                                .port(8080)
                                .addPathSegment("employees")
                                .addPathSegment(String.valueOf(id))
                                .build())
                        .header("Content-Type", "application/json")
                        .delete()
                        .build())
                .execute()) {
            return HttpResponse.fromOkHttpResponse(response);
        } catch (IOException e) {
            throw new ApiException("Failed to delete employee by ID '%s'"
                    .formatted(id), e);
        }
    }

}
