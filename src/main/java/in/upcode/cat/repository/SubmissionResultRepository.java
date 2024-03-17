package in.upcode.cat.repository;

import in.upcode.cat.domain.SubmissionResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SubmissionResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubmissionResultRepository extends MongoRepository<SubmissionResult, String> {}
