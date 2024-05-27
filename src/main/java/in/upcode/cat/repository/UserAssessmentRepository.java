package in.upcode.cat.repository;

import in.upcode.cat.domain.Assessment;
import in.upcode.cat.domain.UserAssessment;
import in.upcode.cat.service.dto.UserAssessmentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the UserAssessment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAssessmentRepository extends MongoRepository<UserAssessment, String> {
    Page<UserAssessment> findByStatus(String type, Pageable pageable);
}
