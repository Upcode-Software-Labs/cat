package in.upcode.cat.repository;

import in.upcode.cat.domain.ValidationRule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ValidationRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValidationRuleRepository extends MongoRepository<ValidationRule, String> {}
