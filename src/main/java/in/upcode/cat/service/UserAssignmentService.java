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
 * Service Implementation for managing {@link in.upcode.cat.domain.UserAssignment}.
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
     * Save a userAssignment.
     *
     * @param userAssignmentDTO the entity to save.
     * @return the persisted entity.
     */
    public UserAssignmentDTO save(UserAssignmentDTO userAssignmentDTO) {
        log.debug("Request to save UserAssignment : {}", userAssignmentDTO);
        UserAssignment userAssignment = userAssignmentMapper.toEntity(userAssignmentDTO);
        userAssignment = userAssignmentRepository.save(userAssignment);
        return userAssignmentMapper.toDto(userAssignment);
    }

    /**
     * Update a userAssignment.
     *
     * @param userAssignmentDTO the entity to save.
     * @return the persisted entity.
     */
    public UserAssignmentDTO update(UserAssignmentDTO userAssignmentDTO) {
        log.debug("Request to update UserAssignment : {}", userAssignmentDTO);
        UserAssignment userAssignment = userAssignmentMapper.toEntity(userAssignmentDTO);
        userAssignment = userAssignmentRepository.save(userAssignment);
        return userAssignmentMapper.toDto(userAssignment);
    }

    /**
     * Partially update a userAssignment.
     *
     * @param userAssignmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserAssignmentDTO> partialUpdate(UserAssignmentDTO userAssignmentDTO) {
        log.debug("Request to partially update UserAssignment : {}", userAssignmentDTO);

        return userAssignmentRepository
            .findById(userAssignmentDTO.getId())
            .map(existingUserAssignment -> {
                userAssignmentMapper.partialUpdate(existingUserAssignment, userAssignmentDTO);

                return existingUserAssignment;
            })
            .map(userAssignmentRepository::save)
            .map(userAssignmentMapper::toDto);
    }

    /**
     * Get all the userAssignments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<UserAssignmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserAssignments");
        return userAssignmentRepository.findAll(pageable).map(userAssignmentMapper::toDto);
    }

    /**
     * Get one userAssignment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<UserAssignmentDTO> findOne(String id) {
        log.debug("Request to get UserAssignment : {}", id);
        return userAssignmentRepository.findById(id).map(userAssignmentMapper::toDto);
    }

    /**
     * Delete the userAssignment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete UserAssignment : {}", id);
        userAssignmentRepository.deleteById(id);
    }
}
