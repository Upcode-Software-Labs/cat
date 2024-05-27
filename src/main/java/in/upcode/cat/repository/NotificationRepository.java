package in.upcode.cat.repository;

import in.upcode.cat.domain.Notification;
import in.upcode.cat.domain.Submission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    Page<Notification> findByRecipient_Id(String id, Pageable pageable);
}
