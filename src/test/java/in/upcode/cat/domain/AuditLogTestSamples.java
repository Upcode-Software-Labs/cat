package in.upcode.cat.domain;

import java.util.UUID;

public class AuditLogTestSamples {

    public static AuditLog getAuditLogSample1() {
        return new AuditLog().id("id1").action("action1");
    }

    public static AuditLog getAuditLogSample2() {
        return new AuditLog().id("id2").action("action2");
    }

    public static AuditLog getAuditLogRandomSampleGenerator() {
        return new AuditLog().id(UUID.randomUUID().toString()).action(UUID.randomUUID().toString());
    }
}
