package in.upcode.cat.service;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubmissionSubmitService {

    public static final Logger log = LoggerFactory.getLogger(SubmissionSubmitService.class);

    public static String getRawContent(String rawUrl) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(new URI(rawUrl)).GET().build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == HttpURLConnection.HTTP_OK) {
            return response.body();
        } else {
            throw new IOException("Failed to fetch raw content. Status code: " + response.statusCode());
        }
    }

    public static String codeCheck(String javaCode) throws CheckstyleException, IOException {
        // Create a Checkstyle Checker
        Checker checker = new Checker();
        // Load Checkstyle configuration
        Configuration config = ConfigurationLoader.loadConfiguration("checkstyle-temp.xml", null);
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(config);

        // Write file contents to a temporary file
        File tempFile = createTempFile("temp", javaCode);

        List<File> fileList = List.of(tempFile);

        // Create custom AuditListener to capture Checkstyle results
        CustomAuditListener auditListener = new CustomAuditListener();
        checker.addListener(auditListener);

        // Perform the Checkstyle check on the file
        checker.process(fileList);

        // Close the Checkstyle checker
        checker.destroy();

        return (auditListener.getViolationsAsString().isEmpty()) ? "no violations" : auditListener.getViolationsAsString();
    }

    private static File createTempFile(String fileName, String content) throws IOException {
        final File tempFile = File.createTempFile(fileName, ".java");

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(content);
        }

        return tempFile;
    }

    public static String[] extractUsernameAndRepo(String url) {
        // Remove any trailing slash from the URL
        url = url.replaceAll("/$", "");

        // Split the URL by "/" to get individual parts
        String[] parts = url.split("/");

        // Extract the username and repository name
        String username = parts[parts.length - 2]; // Second-to-last part
        String repoName = parts[parts.length - 1]; // Last part

        return new String[] { username, repoName };
    }

    public static String analyzeJavaFile(String fileName, String fileContent) throws Exception {
        log.debug("analysing files...");
        // Create Checker instance
        Checker checker = new Checker();

        // Load Checkstyle configuration
        Configuration config = ConfigurationLoader.loadConfiguration("checkstyle-temp.xml", null);
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(config);

        // Configure Checker
        checker.configure(config);

        // Create custom AuditListener to capture Checkstyle results
        CustomAuditListener auditListener = new CustomAuditListener();
        checker.addListener(auditListener);

        // Write file contents to a temporary file
        File tempFile = createTempFile(fileName, fileContent);

        List<File> fileList = List.of(tempFile);

        checker.process(fileList);

        // Close checker
        checker.destroy();

        // Generate quality report
        return (auditListener.getViolationsAsString().isEmpty()) ? "no violations" : auditListener.getViolationsAsString();
    }
}
