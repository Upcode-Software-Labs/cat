package in.upcode.cat.service;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubmissionSubmitService {

    private final Logger log = LoggerFactory.getLogger(SubmissionSubmitService.class);

    public static String getRawContent(String rawUrl) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(new URI(rawUrl)).GET().build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new IOException("Failed to fetch raw content. Status code: " + response.statusCode());
        }
    }

    public static String codeCheck(String javaCode) throws CheckstyleException, IOException {
        String result;

        // Create a Checkstyle Checker
        Checker checker = new Checker();
        // Load Checkstyle configuration
        Configuration config = ConfigurationLoader.loadConfiguration("checkstyle-temp.xml", null);
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(config);

        // Write file contents to a temporary file
        File tempFile = createTempFile(javaCode);

        List<File> fileList = List.of(tempFile);

        // Create custom AuditListener to capture Checkstyle results
        CustomAuditListener auditListener = new CustomAuditListener();
        checker.addListener(auditListener);

        // Perform the Checkstyle check on the file
        checker.process(fileList);

        // Close the Checkstyle checker
        checker.destroy();

        if (auditListener.getViolationsAsString().length() == 0) {
            result = "no violations";
        } else {
            result = auditListener.getViolationsAsString();
        }

        return result;
    }

    private static File createTempFile(String content) throws IOException {
        File tempFile = File.createTempFile("tempJavaFile", ".java");

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(content);
        }

        return tempFile;
    }
}
