package in.upcode.cat.repository;

import in.upcode.cat.domain.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Question entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {}
