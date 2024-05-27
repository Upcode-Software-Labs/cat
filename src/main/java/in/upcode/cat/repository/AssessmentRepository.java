package in.upcode.cat.repository;

import in.upcode.cat.domain.Assessment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Assessment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssessmentRepository extends MongoRepository<Assessment, String> {
    Page<Assessment> findByTypeRegexIgnoreCase(String type, Pageable pageable);

    Optional<Assessment> findByTypeRegexIgnoreCase(String type);
}
