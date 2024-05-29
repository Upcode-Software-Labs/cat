package in.upcode.cat.service;

import in.upcode.cat.domain.UserAssignment;
import in.upcode.cat.repository.UserAssignmentRepository;
import in.upcode.cat.service.dto.UserAssignmentDTO;
import in.upcode.cat.service.mapper.UserAssignmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link UserAssignment}.
 */
@Service
public class UserAssignmentService {

    private final Logger log = LoggerFactory.getLogger(UserAssignmentService.class);

    private final UserAssignmentRepository userAssignmentRepository;

    private final UserAssignmentMapper userAssignmentMapper;

    public UserAssignmentService(UserAssignmentRepository userAssignmentRepository, UserAssignmentMapper userAssignmentMapper) {
        this.userAssignmentRepository = userAssignmentRepository;
        this.userAssignmentMapper = userAssignmentMapper;
    }

    /**
     * Save a userAssessment.
     *
     * @param userAssignmentDTO the entity to save.
     * @return the persisted entity.
     */
    public UserAssignmentDTO save(UserAssignmentDTO userAssignmentDTO) {
        log.debug("Request to save UserAssessment : {}", userAssignmentDTO);
        UserAssignment userAssignment = userAssignmentMapper.toEntity(userAssignmentDTO);
        userAssignment = userAssignmentRepository.save(userAssignment);
        return userAssignmentMapper.toDto(userAssignment);
    }

    /**
     * Update a userAssessment.
     *
     * @param userAssignmentDTO the entity to save.
     * @return the persisted entity.
     */
    public UserAssignmentDTO update(UserAssignmentDTO userAssignmentDTO) {
        log.debug("Request to update UserAssessment : {}", userAssignmentDTO);
        UserAssignment userAssignment = userAssignmentMapper.toEntity(userAssignmentDTO);
        userAssignment = userAssignmentRepository.save(userAssignment);
        return userAssignmentMapper.toDto(userAssignment);
    }

    /**
     * Partially update a userAssessment.
     *
     * @param userAssignmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserAssignmentDTO> partialUpdate(UserAssignmentDTO userAssignmentDTO) {
        log.debug("Request to partially update UserAssessment : {}", userAssignmentDTO);

        return userAssignmentRepository
            .findById(userAssignmentDTO.getId())
            .map(existingUserAssessment -> {
                userAssignmentMapper.partialUpdate(existingUserAssessment, userAssignmentDTO);

                return existingUserAssessment;
            })
            .map(userAssignmentRepository::save)
            .map(userAssignmentMapper::toDto);
    }

    /**
     * Get all the userAssessments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<UserAssignmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserAssessments");
        return userAssignmentRepository.findAll(pageable).map(userAssignmentMapper::toDto);
    }

    /**
     * Get all the userAssessments.
     *
     *  @param status the pagination information.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<UserAssignmentDTO> findBySearch(String status, Pageable pageable) {
        log.debug("Request to get UserAssessments based on status");
        return userAssignmentRepository.findByStatus(status, pageable).map(userAssignmentMapper::toDto);
    }

    /**
     * Get one userAssessment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<UserAssignmentDTO> findOne(String id) {
        log.debug("Request to get UserAssessment : {}", id);
        return userAssignmentRepository.findById(id).map(userAssignmentMapper::toDto);
    }

    /**
     * Delete the userAssessment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete UserAssessment : {}", id);
        userAssignmentRepository.deleteById(id);
    }
}
