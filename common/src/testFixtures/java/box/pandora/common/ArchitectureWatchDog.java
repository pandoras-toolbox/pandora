package box.pandora.common;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

import java.util.Arrays;
import java.util.Locale;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods;
import static com.tngtech.archunit.library.GeneralCodingRules.ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.BE_ANNOTATED_WITH_AN_INJECTION_ANNOTATION;
import static com.tngtech.archunit.library.GeneralCodingRules.THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.library.GeneralCodingRules.USE_JAVA_UTIL_LOGGING;
import static com.tngtech.archunit.library.GeneralCodingRules.USE_JODATIME;

// See the reason for ArchRule.allowEmptyShould(...):
// https://github.com/TNG/ArchUnit/issues/532#issuecomment-1079900509
public final class ArchitectureWatchDog {

    private final JavaClasses classes;

    public ArchitectureWatchDog(String... packages) {
        classes = new ClassFileImporter().importPackages(packages);
        validatePackages(packages);
    }

    private void validatePackages(String[] packages) {
        for (var current : packages) {
            // Don't check classes from 3rd party libraries:
            var prefix = "box.pandora.";
            if (!current.startsWith(prefix)) {
                throw new IllegalStateException("Names must start with '%s' for given packages: %s"
                        .formatted(prefix, Arrays.toString(packages)));
            }
        }
        if (classes.isEmpty()) {
            throw new IllegalStateException("No classes were imported for given packages: %s"
                    .formatted(Arrays.toString(packages)));
        }
    }

    // Prevent SonarQube warning: Mutable collection or array members should not be stored or returned directly
    @SuppressWarnings("java:S2384")
    public JavaClasses getClasses() {
        return classes;
    }

    public void checkGeneralCodingRules() {
        // We can't use the constants of com.tngtech.archunit.library.GeneralCodingRules
        // because we want to exclude classes annotated with IgnoredByArchUnit.
        noClasses()
                .that()
                .areNotAnnotatedWith(IgnoredByArchUnit.class)
                .should().callConstructor(AssertionError.class /* without detailMessage */)
                .because("assertions should have a detail message")
                .check(classes);
        noClasses()
                .that()
                .areNotAnnotatedWith(IgnoredByArchUnit.class)
                .should().dependOnClassesThat().haveFullyQualifiedName("java.sql.Date")
                .orShould().dependOnClassesThat().haveFullyQualifiedName("java.sql.Time")
                .orShould().dependOnClassesThat().haveFullyQualifiedName("java.sql.Timestamp")
                .orShould().dependOnClassesThat().haveFullyQualifiedName("java.util.Calendar")
                .orShould().dependOnClassesThat().haveFullyQualifiedName("java.util.Date")
                .as("java.time API should be used")
                .because("legacy date/time APIs have been replaced since Java 8 (JSR 310)")
                .check(classes);
        noClasses()
                .that()
                .areNotAnnotatedWith(IgnoredByArchUnit.class)
                .should(ACCESS_STANDARD_STREAMS)
                .check(classes);
        noFields()
                .that()
                .areNotAnnotatedWith(IgnoredByArchUnit.class)
                .should(BE_ANNOTATED_WITH_AN_INJECTION_ANNOTATION)
                .as("no classes should use field injection")
                .because("field injection is considered harmful; use constructor injection or setter injection instead; "
                        + "see https://stackoverflow.com/q/39890849 for detailed explanations")
                // Without the next line it would fail for the "rss" module because of no rule matched the 'that()' clause.
                .allowEmptyShould(true)
                .check(classes);
        noClasses()
                .that()
                .areNotAnnotatedWith(IgnoredByArchUnit.class)
                .should(USE_JAVA_UTIL_LOGGING)
                .check(classes);
        noClasses()
                .that()
                .areNotAnnotatedWith(IgnoredByArchUnit.class)
                .should(USE_JODATIME)
                .because("modern Java projects use the [java.time] API instead")
                .check(classes);
        noClasses()
                .that()
                .areNotAnnotatedWith(IgnoredByArchUnit.class)
                .should(THROW_GENERIC_EXCEPTIONS)
                .check(classes);
    }

    public void checkCommonRules() {
        checkStringFormatNotUsed();
        checkJunitAssertionsMustNotBeUsed();
        checkUnsupportedMimeTypesMustNotBeUsed();
        checkOkHttpResponseTypeMustNotBeReturned();
        checkTestsMustNotAccessOkHttpDirectly();
    }

    public void checkStringFormatNotUsed() {
        // Use String.formatted(...) instead.
        noClasses()
                .that()
                .areNotAnnotatedWith(IgnoredByArchUnit.class)
                .and()
                // The methods from that external interface violate the rule.
                .doNotImplement("okhttp3.logging.HttpLoggingInterceptor.Logger")
                .should()
                .callMethod(String.class, "format", String.class, Object[].class)
                .orShould()
                .callMethod(String.class, "format", Locale.class, String.class, Object[].class)
                .allowEmptyShould(true)
                .check(classes);
    }

    public void checkJunitAssertionsMustNotBeUsed() {
        // Use AssertJ instead.
        noClasses()
                .that()
                .areNotAnnotatedWith(IgnoredByArchUnit.class)
                .should()
                .dependOnClassesThat()
                .haveFullyQualifiedName("org.junit.jupiter.api.Assertions")
                .allowEmptyShould(true)
                .check(classes);
    }

    public void checkUnsupportedMimeTypesMustNotBeUsed() {
        // Only our "MediaType" class shall be used.
        noClasses()
                .that()
                .areNotAnnotatedWith(IgnoredByArchUnit.class)
                .should()
                .dependOnClassesThat()
                .haveFullyQualifiedName("org.apache.http.entity.ContentType")
                .orShould()
                .dependOnClassesThat()
                .haveFullyQualifiedName("com.google.common.net.MediaType")
                .orShould()
                .dependOnClassesThat()
                .haveFullyQualifiedName("com.tngtech.archunit.thirdparty.com.google.common.net.MediaType")
                .orShould()
                .dependOnClassesThat()
                .haveFullyQualifiedName("org.apache.tika.mime.MediaType")
                .allowEmptyShould(true)
                .check(classes);
    }

    public void checkOkHttpResponseTypeMustNotBeReturned() {
        // The OkHttp response must be mapped immediately to a surrogate response type and then closed.
        noMethods()
                .that()
                .areNotAnnotatedWith(IgnoredByArchUnit.class)
                .should()
                .haveRawReturnType("okhttp3.Response")
                .allowEmptyShould(true)
                .check(classes);
    }

    public void checkTestsMustNotAccessOkHttpDirectly() {
        noClasses()
                .that()
                .areNotAnnotatedWith(IgnoredByArchUnit.class)
                .and()
                .haveSimpleNameEndingWith("Test")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("okhttp3..")
                .allowEmptyShould(true)
                .check(classes);
    }

}
