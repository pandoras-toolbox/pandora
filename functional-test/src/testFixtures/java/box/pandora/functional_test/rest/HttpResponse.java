package box.pandora.functional_test.rest;

import box.pandora.functional_test.ApiException;
import box.pandora.functional_test.ImmutablesStyle;
import okhttp3.Response;
import org.immutables.builder.Builder;
import org.immutables.value.Value;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

// A custom response type is used because then the original OkHttp response can be closed as soon as possible.
@Value.Immutable
@ImmutablesStyle
public abstract class HttpResponse {

    public abstract Instant sentRequestAt();

    public abstract Instant receivedResponseAt();

    public abstract int code();

    public abstract String message();

    public abstract boolean isSuccessful();

    public abstract boolean isRedirect();

    public abstract Map<String, List<String>> headersMultiMap();

    public abstract Optional<HttpResponseBody> body();

    @Value.Derived
    public Optional<String> header(String name) {
        Optional<String> result = Optional.empty();
        var values = headersMultiMap().get(name);
        if (values != null) {
            var size = values.size();
            if (size == 1) {
                result = Optional.of(values.getFirst());
            }
        }
        return result;
    }

    @Value.Derived
    public List<String> headers(String name) {
        return headersMultiMap().get(name);
    }

    @Builder.Factory
    public static HttpResponse fromOkHttpResponse(Response response) {
        try {
            var responseBuilder = ImmutableHttpResponse.builder()
                    .sentRequestAt(Instant.ofEpochMilli(response.sentRequestAtMillis()))
                    .receivedResponseAt(Instant.ofEpochMilli(response.receivedResponseAtMillis()))
                    .code(response.code())
                    .message(response.message())
                    .isSuccessful(response.isSuccessful())
                    .isRedirect(response.isRedirect())
                    .headersMultiMap(response.headers().toMultimap());
            var body = response.body();
            if (body != null) {
                var bytes = body.bytes();
                var bodyBuilder = ImmutableHttpResponseBody.builder()
                        .contentLength(body.contentLength())
                        .bytes(bytes);
                if (body.contentType() != null) {
                    bodyBuilder.contentType(Objects.requireNonNull(body.contentType()));
                }
                responseBuilder.body(bodyBuilder.build());
            }
            return responseBuilder.build();
        } catch (IOException e) {
            throw new ApiException("Failed to convert OkHttp response: %s"
                    .formatted(response), e);
        }
    }

}
