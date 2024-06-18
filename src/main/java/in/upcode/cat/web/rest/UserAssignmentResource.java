package in.upcode.cat.web.rest;

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
 * REST controller for managing {@link in.upcode.cat.domain.UserAssignment}.
 */
@RestController
@RequestMapping("/api/user-assignments")
public class UserAssignmentResource {

    private final Logger log = LoggerFactory.getLogger(UserAssignmentResource.class);

    private static final String ENTITY_NAME = "userAssignment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAssignmentService userAssignmentService;

    private final UserAssignmentRepository userAssignmentRepository;

    public UserAssignmentResource(UserAssignmentService userAssignmentService, UserAssignmentRepository userAssignmentRepository) {
        this.userAssignmentService = userAssignmentService;
        this.userAssignmentRepository = userAssignmentRepository;
    }

    /**
     * {@code POST  /user-assignments} : Create a new userAssignment.
     *
     * @param userAssignmentDTO the userAssignmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAssignmentDTO, or with status {@code 400 (Bad Request)} if the userAssignment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UserAssignmentDTO> createUserAssignment(@Valid @RequestBody UserAssignmentDTO userAssignmentDTO)
        throws URISyntaxException {
        log.debug("REST request to save UserAssignment : {}", userAssignmentDTO);
        if (userAssignmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new userAssignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAssignmentDTO result = userAssignmentService.save(userAssignmentDTO);
        return ResponseEntity
            .created(new URI("/api/user-assignments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /user-assignments/:id} : Updates an existing userAssignment.
     *
     * @param id the id of the userAssignmentDTO to save.
     * @param userAssignmentDTO the userAssignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAssignmentDTO,
     * or with status {@code 400 (Bad Request)} if the userAssignmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAssignmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserAssignmentDTO> updateUserAssignment(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody UserAssignmentDTO userAssignmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserAssignment : {}, {}", id, userAssignmentDTO);
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
     * {@code PATCH  /user-assignments/:id} : Partial updates given fields of an existing userAssignment, field will ignore if it is null
     *
     * @param id the id of the userAssignmentDTO to save.
     * @param userAssignmentDTO the userAssignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAssignmentDTO,
     * or with status {@code 400 (Bad Request)} if the userAssignmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userAssignmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userAssignmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserAssignmentDTO> partialUpdateUserAssignment(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody UserAssignmentDTO userAssignmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserAssignment partially : {}, {}", id, userAssignmentDTO);
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
     * {@code GET  /user-assignments} : get all the userAssignments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAssignments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<UserAssignmentDTO>> getAllUserAssignments(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of UserAssignments");
        Page<UserAssignmentDTO> page = userAssignmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-assignments/:id} : get the "id" userAssignment.
     *
     * @param id the id of the userAssignmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAssignmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserAssignmentDTO> getUserAssignment(@PathVariable("id") String id) {
        log.debug("REST request to get UserAssignment : {}", id);
        Optional<UserAssignmentDTO> userAssignmentDTO = userAssignmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAssignmentDTO);
    }

    /**
     * {@code DELETE  /user-assignments/:id} : delete the "id" userAssignment.
     *
     * @param id the id of the userAssignmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAssignment(@PathVariable("id") String id) {
        log.debug("REST request to delete UserAssignment : {}", id);
        userAssignmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
