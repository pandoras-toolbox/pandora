package box.pandora.core;

import box.pandora.core.config.LoggingConfig;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;

public final class JsonUtil {

    private JsonUtil() {
    }

    public static String asJson(Object object) {
        try {
            return buildDefaultObjectMapper()
                    .writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to convert object to JSON: %s"
                    .formatted(object), e);
        }
    }

    // Converting an object to JSON makes it better readable for logging and the Allure report.
    public static String asPrettyJson(Object object) {
        try {
            return buildDefaultObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to convert object to JSON: %s"
                    .formatted(object), e);
        }
    }

    // Converting an object to JSON makes it better readable for logging and the Allure report.
    public static String asPrettyJson(String content) {
        try {
            var objectMapper = buildDefaultObjectMapper();
            var jsonObject = objectMapper.readValue(content, Object.class);
            return objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to convert string to JSON: %s"
                    .formatted(content), e);
        }
    }

    public static <T> T jsonToObject(String json, TypeReference<T> typeReference) {
        try {
            return buildDefaultObjectMapper()
                    .readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            var abbreviatedJson = StringUtils.abbreviate(json, LoggingConfig.maximumLengthResponse());
            throw new IllegalStateException("Failed mapping to %s the JSON: %s"
                    .formatted(typeReference.getType().getTypeName(), abbreviatedJson), e);
        }
    }

    public static ObjectMapper buildDefaultObjectMapper() {
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
                // https://stackoverflow.com/a/54023186
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

}
