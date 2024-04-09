package in.upcode.cat.service;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import java.io.File;
import java.util.logging.Logger;

public class CustomAuditListener implements AuditListener {

    private final StringBuilder violations = new StringBuilder();
    private static final Logger logger = Logger.getLogger(CustomAuditListener.class.getName());

    @Override
    public void auditStarted(AuditEvent event) {
        // Called when the audit starts
        logger.info("Audit started");
    }

    @Override
    public void auditFinished(AuditEvent event) {
        // Called when the audit finishes
        logger.info("Audit finished");
    }

    @Override
    public void fileStarted(AuditEvent event) {
        // Called when a new file is started
        logger.info("File started: " + event.getFileName());
    }

    @Override
    public void fileFinished(AuditEvent event) {
        // Called when a file is finished
        logger.info("File finished: " + event.getFileName());
    }

    @Override
    public void addError(AuditEvent auditEvent) {
        violations.append("Error in file: ").append(auditEvent.getFileName()).append("\n");
        violations.append(auditEvent.getMessage()).append("\n");
    }

    @Override
    public void addException(AuditEvent event, Throwable throwable) {
        // Called when an exception occurs
        logger.info("Exception: " + throwable.getMessage());
    }

    public String getViolationsAsString() {
        return violations.toString();
    }
}
