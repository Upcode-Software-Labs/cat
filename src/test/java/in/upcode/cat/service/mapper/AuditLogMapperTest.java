package in.upcode.cat.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class AuditLogMapperTest {

    private AuditLogMapper auditLogMapper;

    @BeforeEach
    public void setUp() {
        auditLogMapper = new AuditLogMapperImpl();
    }
}
