package box.pandora.functional_test;

import org.immutables.value.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PACKAGE, ElementType.TYPE})
// Make it class retention for incremental compilation:
@Retention(RetentionPolicy.CLASS)
@Value.Style(get = "*", set = "*", redactedMask = "[MASKED]")
public @interface ImmutablesStyle {
    // https://immutables.github.io/style.html
}
