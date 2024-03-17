package in.upcode.cat.repository;

import in.upcode.cat.domain.UserAssessment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the UserAssessment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAssessmentRepository extends MongoRepository<UserAssessment, String> {}
