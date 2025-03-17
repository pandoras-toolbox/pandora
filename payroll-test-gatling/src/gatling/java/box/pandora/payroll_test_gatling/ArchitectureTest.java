package box.pandora.payroll_test_gatling;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.GeneralCodingRules;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("architecture")
final class ArchitectureTest {

    @Test
    void checkArchitecture() {
        var javaClasses = new ClassFileImporter().importPackages(getClass().getPackageName());
        checkGeneralCodingRules(javaClasses);
    }

    private static void checkGeneralCodingRules(JavaClasses classes) {
        GeneralCodingRules.ASSERTIONS_SHOULD_HAVE_DETAIL_MESSAGE.check(classes);
        GeneralCodingRules.OLD_DATE_AND_TIME_CLASSES_SHOULD_NOT_BE_USED.check(classes);
        GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS.check(classes);
        GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION.check(classes);
        GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING.check(classes);
        GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME.check(classes);
        GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS.check(classes);
    }

}
