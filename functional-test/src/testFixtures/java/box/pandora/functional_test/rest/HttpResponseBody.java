package box.pandora.functional_test.rest;

import box.pandora.functional_test.ImmutablesStyle;
import com.google.common.base.MoreObjects;
import okhttp3.MediaType;
import org.apache.commons.lang3.StringUtils;
import org.immutables.value.Value;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

// We use a custom response type so that the original OkHttp response can be closed immediately.
@Value.Immutable
@ImmutablesStyle
public abstract class HttpResponseBody {

    public abstract long contentLength();

    public abstract Optional<MediaType> contentType();

    public abstract byte[] bytes();

    @Value.Derived
    public String string() {
        return new String(bytes(), StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        // The toString() of this object can be too big, Allure report creation could fail in such a case.
        return MoreObjects.toStringHelper(HttpResponseBody.class.getSimpleName())
                .omitNullValues()
                .add("contentLength", contentLength())
                .add("contentType", contentType())
                .add("bytes (length)", bytes().length)
                .add("string", StringUtils.abbreviate(string(), 987))
                .toString();
    }

}
