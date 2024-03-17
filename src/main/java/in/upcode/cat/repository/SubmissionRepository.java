package in.upcode.cat.repository;

import in.upcode.cat.domain.Submission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Submission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubmissionRepository extends MongoRepository<Submission, String> {}
