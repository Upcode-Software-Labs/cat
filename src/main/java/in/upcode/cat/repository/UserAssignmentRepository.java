package in.upcode.cat.repository;

import in.upcode.cat.domain.UserAssignment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the UserAssignment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAssignmentRepository extends MongoRepository<UserAssignment, String> {}
