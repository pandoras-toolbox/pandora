package box.pandora.functional_test;

import box.pandora.core.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import io.qameta.allure.Step;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static box.pandora.functional_test.allure.DynamicStep.step;

public final class JsonPathEvaluator {

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

    @Step("select properties at JSON path {jsonPath}")
    public <T> List<T> asList(String jsonPath, Class<T> type) {
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
        step("the selected properties are: %s".formatted(StringUtils.join(result, ", ")));
        return result;
    }

    private static <T> void validateType(List<T> list, Class<T> expectedType, String jsonString) {
        if (!list.isEmpty()) {
            var actualType = list.get(0).getClass();
            if (!actualType.equals(expectedType)) {
                throw new IllegalStateException("Expected type %s but was %s for JSON array: %s"
                        .formatted(expectedType.getName(), actualType.getName(), jsonString));
            }
        }
    }

    @Step("select property at JSON path {jsonPath}")
    // Prevent SonarQube warning: Exception handlers should preserve the original exceptions
    @SuppressWarnings("java:S1166")
    private <T> Optional<T> read(String jsonPath, Class<T> type) {
        Optional<T> result = Optional.empty();
        try {
            var read = documentContext.read(jsonPath);
            result = Optional.ofNullable(type.cast(read));
        } catch (PathNotFoundException e) {
            // Exception can be ignored.
        }
        if (result.isEmpty()) {
            step("the selected property is: null");
        } else {
            step("the selected property is: %s".formatted(result.get()));
        }
        return result;
    }

}
