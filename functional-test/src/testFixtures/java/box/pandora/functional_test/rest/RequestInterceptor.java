package box.pandora.functional_test.rest;

import io.qameta.allure.Allure;
import okhttp3.Interceptor;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class RequestInterceptor implements Interceptor {

    private static final Logger LOGGER = LogManager.getLogger();

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        var request = chain.request();
        // TODO https://sws.atlassian.net/browse/SES-16629
        // TODO https://sws.atlassian.net/browse/SES-16638
        try {
            return chain.proceed(request);
        } catch (IOException e) {
            // In case of a java.net.ConnectException we would otherwise have no logging of the request.
            var requestString = request.toString();
            LOGGER.error("Failed request: {}", requestString, e);
            Allure.addAttachment("Failed request", requestString);
            var requestBody = request.body();
            if (requestBody != null) {
                var requestBodyString = requestBodyToString(requestBody);
                LOGGER.error("Failed request body: {}", requestBodyString, e);
                Allure.addAttachment("Failed request body", requestBodyString);
            }
            throw e;
        }
    }

    private static String requestBodyToString(RequestBody requestBody) throws IOException {
        var buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.readUtf8();
    }

}
