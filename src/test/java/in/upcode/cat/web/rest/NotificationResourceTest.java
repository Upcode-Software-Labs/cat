package in.upcode.cat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import in.upcode.cat.domain.Notification;
import in.upcode.cat.domain.Submission;
import in.upcode.cat.domain.enumeration.NotificationStatus;
import in.upcode.cat.domain.enumeration.NotificationType;
import in.upcode.cat.repository.NotificationRepository;
import in.upcode.cat.service.dto.NotificationDTO;
import in.upcode.cat.service.mapper.NotificationMapper;
import java.util.Base64;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class NotificationResourceTest {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final NotificationType DEFAULT_NOTIFICATIONTYPE = NotificationType.ASSIGNMENT;
    private static final NotificationType UPDATED_NOTIFICATIONTYPE = NotificationType.SUBMISSION;

    private static final NotificationStatus DEFAULT_STATUS = NotificationStatus.UNREAD;
    private static final NotificationStatus UPDATED_STATUS = NotificationStatus.READ;

    private static final String ENTITY_API_URL = "/api/notifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    private MockMvc restNotificationMockMvc;

    Notification notification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notification createEntity() {
        Notification notification = new Notification()
            .message(DEFAULT_MESSAGE)
            .notificationType(DEFAULT_NOTIFICATIONTYPE)
            .status(DEFAULT_STATUS);

        return notification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notification createUpdatedEntity() {
        Notification notification = new Notification()
            .message(UPDATED_MESSAGE)
            .notificationType(UPDATED_NOTIFICATIONTYPE)
            .status(UPDATED_STATUS);

        return notification;
    }

    @BeforeEach
    public void initTest() {
        notificationRepository.deleteAll();
        notification = createEntity();
    }

    @Test
    void createNotification() throws Exception {
        int databaseSizeBeforeCreate = notificationRepository.findAll().size();

        // Create the Notification DTO
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // Perform POST request to create the notification
        restNotificationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notificationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate + 1);

        // Get the last created notification
        Notification testNotification = notificationList.get(notificationList.size() - 1);

        // Assert that the created notification matches the expected values
        assertThat(testNotification.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testNotification.getNotificationType()).isEqualTo(DEFAULT_NOTIFICATIONTYPE);
        assertThat(testNotification.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    void getAllNotifications() throws Exception {
        // Initialize the database
        notificationRepository.save(notification);

        // Get all the submissionList
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].notificationType").value(hasItem(DEFAULT_NOTIFICATIONTYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
}
