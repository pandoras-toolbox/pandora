package box.pandora.functional_test.rest;

import box.pandora.core.config.LoggingConfig;
import box.pandora.core.config.OkHttpConfig;
import io.qameta.allure.Allure;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

// An easy way to reuse OkHttp's request/response toString conversions,
// but not very safe regarding changes in that 3rd party library.
// Prevent SonarQube warning: Synchronized classes "Vector", "Hashtable", "Stack" and "StringBuffer" should not be used
@SuppressWarnings("java:S1149")
public final class HttpLogger implements HttpLoggingInterceptor.Logger {

    private static final Logger LOGGER = LogManager.getLogger();

    private final StringBuffer request = new StringBuffer();
    private final StringBuffer prettyRequest = new StringBuffer();

    private final StringBuffer response = new StringBuffer();
    private final StringBuffer prettyResponse = new StringBuffer();

    private final boolean prettyLogging;
    private final List<String> secretsToMask;

    private InterceptorState state;

    public HttpLogger(boolean prettyLogging, List<String> secretsToMask) {
        this.prettyLogging = prettyLogging;
        this.secretsToMask = Collections.unmodifiableList(secretsToMask);
    }

    @Override
    public void log(@NotNull String s) {
        append(s);
        if (state == InterceptorState.REQUEST_ENDED || state == InterceptorState.RESPONSE_ENDED) {
            log(state);
        }
    }

    private void append(String text) {
        if (text.startsWith(InterceptorState.REQUEST_ENDED.identifier())) {
            state = InterceptorState.REQUEST_ENDED;
        } else if (text.startsWith(InterceptorState.REQUEST_STARTED.identifier())) {
            state = InterceptorState.REQUEST_STARTED;
        } else if (text.startsWith(InterceptorState.RESPONSE_ENDED.identifier())) {
            state = InterceptorState.RESPONSE_ENDED;
        } else if (text.startsWith(InterceptorState.RESPONSE_STARTED.identifier())) {
            state = InterceptorState.RESPONSE_STARTED;
        } else {
            LOGGER.trace("Ignoring text: {}", text);
        }
        append(text, state);
    }

    private void append(String text, InterceptorState state) {
        var safeText = text;
        for (String secret : secretsToMask) {
            safeText = safeText.replace(secret, "[MASKED]");
        }
        switch (state) {
            case REQUEST_STARTED, REQUEST_ENDED -> appendRequest(safeText);
            case RESPONSE_STARTED, RESPONSE_ENDED -> appendResponse(safeText);
            default -> throw new IllegalStateException("Unsupported state: %s".formatted(state));
        }
    }

    private void appendRequest(String text) {
        var effectiveText = determineEffectiveText(text, OkHttpConfig.payloadMaximumLengthRequest());
        if (prettyLogging) {
            prettyRequest.append(effectiveText);
            prettyRequest.append(System.lineSeparator());
        } else {
            request.append(effectiveText);
            request.append(", ");
        }
    }

    private void appendResponse(String text) {
        var effectiveText = determineEffectiveText(text, OkHttpConfig.payloadMaximumLengthResponse());
        if (prettyLogging) {
            prettyResponse.append(effectiveText);
            prettyResponse.append(System.lineSeparator());
        } else {
            response.append(effectiveText);
            response.append(", ");
        }
    }

    private static String determineEffectiveText(String text, int payloadMaximumLength) {
        var tooLarge = text.length() > payloadMaximumLength;
        if (tooLarge) {
            var abbreviatedText = "%n%s".formatted(StringUtils.abbreviate(text, payloadMaximumLength / 13));
            return "[LARGE BODY ↓↓↓, %s]%s"
                    .formatted(FileUtils.byteCountToDisplaySize(text.getBytes(StandardCharsets.UTF_8).length), abbreviatedText);
        }
        return text;
    }

    private void log(InterceptorState state) {
        switch (state) {
            case REQUEST_ENDED -> log("Request", request, prettyRequest, LoggingConfig.maximumLengthRequest());
            case RESPONSE_ENDED -> log("Response", response, prettyResponse, LoggingConfig.maximumLengthResponse());
            default -> throw new IllegalStateException("Unsupported state: %s".formatted(state));
        }
    }

    private void log(String description, StringBuffer message, StringBuffer prettyMessage, int maximumLength) {
        var effectiveMessage = prettyLogging ? prettyMessage : message;
        var abbreviatedMessage = StringUtils.abbreviate(effectiveMessage.toString(), LoggingConfig.abbreviationMarker(), maximumLength);
        LOGGER.info("{}:{}{}",
                description, prettyLogging ? System.lineSeparator() : " ", abbreviatedMessage);
        Allure.addAttachment(description, prettyMessage.toString());
        resetRequestsAndResponses();
    }

    private void resetRequestsAndResponses() {
        request.setLength(0);
        prettyRequest.setLength(0);
        response.setLength(0);
        prettyResponse.setLength(0);
    }

}
