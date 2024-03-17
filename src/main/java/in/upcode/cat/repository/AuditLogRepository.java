package in.upcode.cat.repository;

import in.upcode.cat.domain.AuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the AuditLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuditLogRepository extends MongoRepository<AuditLog, String> {}
