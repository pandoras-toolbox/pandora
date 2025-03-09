package box.pandora.functional_test.allure;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public final class EnvironmentWriter {

    private static final Logger LOGGER = LogManager.getLogger();

    private EnvironmentWriter() {
    }

    public static void writeAllureEnvironmentXml(Map<String, String> environmentValuesSet) {
        try {
            var userDir = System.getProperty("user.dir");
            var allureResultsDir = new File("%s/build/allure-results".formatted(userDir));
            var environmentXmlFile = new File("%s/environment.xml".formatted(allureResultsDir));
            LOGGER.info("Saving Allure environment data to: {}", environmentXmlFile);

            var docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            var document = docBuilder.newDocument();
            var environment = document.createElement("environment");
            document.appendChild(environment);
            environmentValuesSet.forEach((k, v) -> {
                var parameter = document.createElement("parameter");
                var key = document.createElement("key");
                var value = document.createElement("value");
                key.appendChild(document.createTextNode(k));
                value.appendChild(document.createTextNode(v));
                parameter.appendChild(key);
                parameter.appendChild(value);
                environment.appendChild(parameter);
            });

            if (!allureResultsDir.exists()) {
                var success = allureResultsDir.mkdirs();
                if (success) {
                    LOGGER.debug("Successfully created Allure results directory: {}", allureResultsDir);
                } else {
                    throw new IllegalStateException("Failed to create Allure results directory: %s"
                            .formatted(allureResultsDir));
                }
            }
            var result = new StreamResult(environmentXmlFile);
            var transformer = TransformerFactory.newDefaultInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), result);
            var xmlPath = environmentXmlFile.toPath();
            LOGGER.info("Saved content of {} is:{}{}",
                    xmlPath.getFileName(), System.lineSeparator(), Files.readString(xmlPath));
        } catch (ParserConfigurationException | TransformerException | IOException e) {
            throw new IllegalStateException("Failed to write Allure environment file", e);
        }
    }

}
