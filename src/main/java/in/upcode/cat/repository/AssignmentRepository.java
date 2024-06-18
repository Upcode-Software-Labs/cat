package in.upcode.cat.repository;

import in.upcode.cat.domain.Assignment;
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
public interface AssignmentRepository extends MongoRepository<Assignment, String> {
    Page<Assignment> findByType_id(String type, Pageable pageable);

    Optional<Assignment> findByTypeRegexIgnoreCase(String type);
}
