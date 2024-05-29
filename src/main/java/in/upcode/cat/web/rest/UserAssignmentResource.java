package in.upcode.cat.web.rest;

import in.upcode.cat.domain.UserAssignment;
import in.upcode.cat.repository.UserAssignmentRepository;
import in.upcode.cat.service.UserAssignmentService;
import in.upcode.cat.service.dto.UserAssignmentDTO;
import in.upcode.cat.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link UserAssignment}.
 */
@RestController
@RequestMapping("/api/user-assessments")
public class UserAssignmentResource {

    private final Logger log = LoggerFactory.getLogger(UserAssignmentResource.class);

    private static final String ENTITY_NAME = "userAssessment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAssignmentService userAssignmentService;

    private final UserAssignmentRepository userAssignmentRepository;

    public UserAssignmentResource(UserAssignmentService userAssignmentService, UserAssignmentRepository userAssignmentRepository) {
        this.userAssignmentService = userAssignmentService;
        this.userAssignmentRepository = userAssignmentRepository;
    }

    /**
     * {@code POST  /user-assessments} : Create a new userAssessment.
     *
     * @param userAssignmentDTO the userAssessmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAssessmentDTO, or with status {@code 400 (Bad Request)} if the userAssessment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UserAssignmentDTO> createUserAssessment(@Valid @RequestBody UserAssignmentDTO userAssignmentDTO)
        throws URISyntaxException {
        log.debug("REST request to save UserAssessment : {}", userAssignmentDTO);
        if (userAssignmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new userAssessment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAssignmentDTO result = userAssignmentService.save(userAssignmentDTO);
        return ResponseEntity
            .created(new URI("/api/user-assessments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /user-assessments/:id} : Updates an existing userAssessment.
     *
     * @param id the id of the userAssessmentDTO to save.
     * @param userAssignmentDTO the userAssessmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAssessmentDTO,
     * or with status {@code 400 (Bad Request)} if the userAssessmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAssessmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserAssignmentDTO> updateUserAssessment(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody UserAssignmentDTO userAssignmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserAssessment : {}, {}", id, userAssignmentDTO);
        if (userAssignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAssignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserAssignmentDTO result = userAssignmentService.update(userAssignmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userAssignmentDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-assessments/:id} : Partial updates given fields of an existing userAssessment, field will ignore if it is null
     *
     * @param id the id of the userAssessmentDTO to save.
     * @param userAssignmentDTO the userAssessmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAssessmentDTO,
     * or with status {@code 400 (Bad Request)} if the userAssessmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userAssessmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userAssessmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserAssignmentDTO> partialUpdateUserAssessment(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody UserAssignmentDTO userAssignmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserAssessment partially : {}, {}", id, userAssignmentDTO);
        if (userAssignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAssignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserAssignmentDTO> result = userAssignmentService.partialUpdate(userAssignmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userAssignmentDTO.getId())
        );
    }

    /**
     * {@code GET  /user-assessments} : get all the userAssessments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAssessments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<UserAssignmentDTO>> getAllUserAssessments(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of UserAssessments");
        Page<UserAssignmentDTO> page = userAssignmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserAssignmentDTO>> getUserAssessmentBySearch(
        @RequestParam String status,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        Page<UserAssignmentDTO> page = userAssignmentService.findBySearch(status, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-assessments/:id} : get the "id" userAssessment.
     *
     * @param id the id of the userAssessmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAssessmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserAssignmentDTO> getUserAssessment(@PathVariable("id") String id) {
        log.debug("REST request to get UserAssessment : {}", id);
        Optional<UserAssignmentDTO> userAssessmentDTO = userAssignmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAssessmentDTO);
    }

    /**
     * {@code DELETE  /user-assessments/:id} : delete the "id" userAssessment.
     *
     * @param id the id of the userAssessmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAssessment(@PathVariable("id") String id) {
        log.debug("REST request to delete UserAssessment : {}", id);
        userAssignmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
