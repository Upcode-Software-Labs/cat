package in.upcode.cat.repository;

import in.upcode.cat.domain.UserAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the UserAssessment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAssignmentRepository extends MongoRepository<UserAssignment, String> {
    Page<UserAssignment> findByStatus(String type, Pageable pageable);
}
