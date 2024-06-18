package in.upcode.cat.repository;

import in.upcode.cat.domain.Submission;
import in.upcode.cat.domain.UserAssignment;
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
    Page<Submission> findByForAssignment_Assignment_Type_Id(String type, Pageable pageable);

    Page<Submission> findByForAssignment_User_Id(String user, Pageable pageable);

    Page<Submission> findByForAssignment_User_IdAndForAssignment_Assignment_Type_Id(String userId, String typeId, Pageable pageable);
}
