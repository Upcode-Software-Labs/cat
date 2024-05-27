package in.upcode.cat.service;

import in.upcode.cat.domain.Notification;
import in.upcode.cat.repository.NotificationRepository;
import in.upcode.cat.service.dto.NotificationDTO;
import in.upcode.cat.service.mapper.NotificationMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationMapper notificationMapper;

    public NotificationService(NotificationRepository notificationRepository, NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }

    public NotificationDTO save(NotificationDTO notificationDTO) {
        Notification notification = notificationMapper.toEntity(notificationDTO);
        notification = notificationRepository.save(notification);
        return notificationMapper.toDto(notification);
    }

    public Optional<NotificationDTO> findOne(String id) {
        return notificationRepository.findById(id).map(notificationMapper::toDto);
    }

    public void delete(String id) {
        notificationRepository.deleteById(id);
    }

    public Page<NotificationDTO> findAll(Pageable pageable) {
        return notificationRepository.findAll(pageable).map(notificationMapper::toDto);
    }

    public Page<NotificationDTO> findByRecipientId(String userId, Pageable pageable) {
        return notificationRepository.findByRecipient_Id(userId, pageable).map(notificationMapper::toDto);
    }
}
