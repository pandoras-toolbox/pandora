package box.pandora.common;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoredByArchUnit {

    // Enforce to document why a class shall be ignored by ArchUnit rules.
    String reason();

}
