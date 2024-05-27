package in.upcode.cat.repository;

import in.upcode.cat.domain.Submission;
import in.upcode.cat.service.dto.SubmissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Submission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubmissionRepository extends MongoRepository<Submission, String> {
    Page<Submission> findByAssessment_Id(String id, Pageable pageable);

    Page<Submission> findByUser_Id(String userId, Pageable pageable);

    Page<SubmissionDTO> findByForAssignment_Id(String id, Pageable pageable);

    Page<Submission> findByUserIdAndAssessmentId(String userId, String assessmentId, Pageable pageable);
}
