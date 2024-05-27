package in.upcode.cat.web.rest;

import in.upcode.cat.domain.Notification;
import in.upcode.cat.domain.User;
import in.upcode.cat.repository.UserRepository;
import in.upcode.cat.service.NotificationService;
import in.upcode.cat.service.dto.NotificationDTO;
import in.upcode.cat.service.dto.SubmissionDTO;
import in.upcode.cat.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api/notifications")
public class NotificationResource {

    private final Logger log = LoggerFactory.getLogger(NotificationResource.class);

    private static final String ENTITY_NAME = "notification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationService notificationService;

    public NotificationResource(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Notifications");

        Page<NotificationDTO> page = notificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("my-notifications")
    public ResponseEntity<List<NotificationDTO>> getUserNotifications(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        // Retrieve authentication object from security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Retrieve user details from authentication object
        String username = authentication.getName();

        final User userOptional;
        final String userId;

        Optional<User> user = userRepository.findOneByLogin(username);

        if (user.isPresent()) {
            userOptional = user.get();
            userId = userOptional.getId();

            Page<NotificationDTO> page = notificationService.findByRecipientId(userId, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } else {
            return ResponseEntity.ok().body(Collections.emptyList());
        }
    }

    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO)
        throws BadRequestAlertException, URISyntaxException {
        log.debug("REST request to save Notification : {}", notificationDTO);
        if (notificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new notification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotificationDTO result = notificationService.save(notificationDTO);
        return ResponseEntity
            .created(new URI("/api/notofications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code DELETE  /notifications/:id} : delete the "id" assessment.
     *
     * @param id the id of the assessmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable("id") String id) {
        log.debug("REST request to delete Assessment : {}", id);
        notificationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
