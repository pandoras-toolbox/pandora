package box.pandora.functional_test;

import box.pandora.common.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public final class JsonPathEvaluator {

    private static final Logger LOGGER = LogManager.getLogger();

    private final DocumentContext documentContext;

    private JsonPathEvaluator(DocumentContext documentContext) {
        this.documentContext = documentContext;
    }

    public static JsonPathEvaluator parseJson(String json) {
        var documentContext = JsonPath.parse(json);
        return new JsonPathEvaluator(documentContext);
    }

    public Optional<String> asString(String jsonPath) {
        return read(jsonPath, String.class);
    }

    public Optional<Boolean> asBoolean(String jsonPath) {
        return read(jsonPath, Boolean.class);
    }

    public Optional<Integer> asInteger(String jsonPath) {
        return read(jsonPath, Integer.class);
    }

    public Optional<Long> asLong(String jsonPath) {
        return read(jsonPath, Long.class);
    }

    public Optional<Double> asDouble(String jsonPath) {
        return read(jsonPath, Double.class);
    }

    @SuppressWarnings("rawtypes")
    public List<LinkedHashMap> asArrayList(String jsonPath) {
        return asList(jsonPath, LinkedHashMap.class);
    }

    public List<String> asStringList(String jsonPath) {
        return asList(jsonPath, String.class);
    }

    public List<Boolean> asBooleanList(String jsonPath) {
        return asList(jsonPath, Boolean.class);
    }

    public List<Integer> asIntegerList(String jsonPath) {
        return asList(jsonPath, Integer.class);
    }

    public List<Long> asLongList(String jsonPath) {
        return asList(jsonPath, Long.class);
    }

    public List<Double> asDoubleList(String jsonPath) {
        return asList(jsonPath, Double.class);
    }

    public <T> List<T> asList(String jsonPath, Class<T> type) {
        LOGGER.debug("Select properties at JSON path {}", jsonPath);
        var result = new ArrayList<T>();
        if (documentContext.read(jsonPath) instanceof JSONArray jsonArray) {
            try {
                var jsonString = jsonArray.toJSONString();
                result = JsonUtil.buildDefaultObjectMapper()
                        .readValue(jsonString, new TypeReference<>() {
                        });
                // I was not sure how to check this in a more generic/elegant way:
                validateType(result, type, jsonString);
            } catch (JsonProcessingException e) {
                throw new IllegalStateException("Failed to read JSON array: %s".formatted(jsonArray), e);
            }
        }
        LOGGER.debug("The selected properties are: {}", StringUtils.join(result, ", "));
        return result;
    }

    private static <T> void validateType(List<T> list, Class<T> expectedType, String jsonString) {
        if (!list.isEmpty()) {
            var actualType = list.getFirst().getClass();
            if (!actualType.equals(expectedType)) {
                throw new IllegalStateException("Expected type %s but was %s for JSON array: %s"
                        .formatted(expectedType.getName(), actualType.getName(), jsonString));
            }
        }
    }

    // Prevent SonarQube warning: Exception handlers should preserve the original exceptions
    @SuppressWarnings("java:S1166")
    private <T> Optional<T> read(String jsonPath, Class<T> type) {
        LOGGER.debug("Select property at JSON path {}", jsonPath);
        Optional<T> result = Optional.empty();
        try {
            var read = documentContext.read(jsonPath);
            result = Optional.ofNullable(type.cast(read));
        } catch (PathNotFoundException e) {
            // Exception can be ignored.
        }
        if (result.isEmpty()) {
            LOGGER.debug("The selected property is: null");
        } else {
            LOGGER.debug("the selected property is: {}", result.get());
        }
        return result;
    }

}
