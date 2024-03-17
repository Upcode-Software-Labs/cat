package in.upcode.cat.web.rest;

import in.upcode.cat.repository.UserAssessmentRepository;
import in.upcode.cat.service.UserAssessmentService;
import in.upcode.cat.service.dto.UserAssessmentDTO;
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
 * REST controller for managing {@link in.upcode.cat.domain.UserAssessment}.
 */
@RestController
@RequestMapping("/api/user-assessments")
public class UserAssessmentResource {

    private final Logger log = LoggerFactory.getLogger(UserAssessmentResource.class);

    private static final String ENTITY_NAME = "userAssessment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAssessmentService userAssessmentService;

    private final UserAssessmentRepository userAssessmentRepository;

    public UserAssessmentResource(UserAssessmentService userAssessmentService, UserAssessmentRepository userAssessmentRepository) {
        this.userAssessmentService = userAssessmentService;
        this.userAssessmentRepository = userAssessmentRepository;
    }

    /**
     * {@code POST  /user-assessments} : Create a new userAssessment.
     *
     * @param userAssessmentDTO the userAssessmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAssessmentDTO, or with status {@code 400 (Bad Request)} if the userAssessment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UserAssessmentDTO> createUserAssessment(@Valid @RequestBody UserAssessmentDTO userAssessmentDTO)
        throws URISyntaxException {
        log.debug("REST request to save UserAssessment : {}", userAssessmentDTO);
        if (userAssessmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new userAssessment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAssessmentDTO result = userAssessmentService.save(userAssessmentDTO);
        return ResponseEntity
            .created(new URI("/api/user-assessments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /user-assessments/:id} : Updates an existing userAssessment.
     *
     * @param id the id of the userAssessmentDTO to save.
     * @param userAssessmentDTO the userAssessmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAssessmentDTO,
     * or with status {@code 400 (Bad Request)} if the userAssessmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAssessmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserAssessmentDTO> updateUserAssessment(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody UserAssessmentDTO userAssessmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserAssessment : {}, {}", id, userAssessmentDTO);
        if (userAssessmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAssessmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAssessmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserAssessmentDTO result = userAssessmentService.update(userAssessmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userAssessmentDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-assessments/:id} : Partial updates given fields of an existing userAssessment, field will ignore if it is null
     *
     * @param id the id of the userAssessmentDTO to save.
     * @param userAssessmentDTO the userAssessmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAssessmentDTO,
     * or with status {@code 400 (Bad Request)} if the userAssessmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userAssessmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userAssessmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserAssessmentDTO> partialUpdateUserAssessment(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody UserAssessmentDTO userAssessmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserAssessment partially : {}, {}", id, userAssessmentDTO);
        if (userAssessmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAssessmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAssessmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserAssessmentDTO> result = userAssessmentService.partialUpdate(userAssessmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userAssessmentDTO.getId())
        );
    }

    /**
     * {@code GET  /user-assessments} : get all the userAssessments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAssessments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<UserAssessmentDTO>> getAllUserAssessments(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of UserAssessments");
        Page<UserAssessmentDTO> page = userAssessmentService.findAll(pageable);
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
    public ResponseEntity<UserAssessmentDTO> getUserAssessment(@PathVariable("id") String id) {
        log.debug("REST request to get UserAssessment : {}", id);
        Optional<UserAssessmentDTO> userAssessmentDTO = userAssessmentService.findOne(id);
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
        userAssessmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
