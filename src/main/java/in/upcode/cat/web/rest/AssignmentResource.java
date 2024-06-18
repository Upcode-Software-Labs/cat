package in.upcode.cat.web.rest;

import in.upcode.cat.domain.Assignment;
import in.upcode.cat.repository.AssignmentRepository;
import in.upcode.cat.service.AssignmentService;
import in.upcode.cat.service.dto.AssignmentDTO;
import in.upcode.cat.service.dto.CategoryDTO;
import in.upcode.cat.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalTime;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link Assignment}.
 */
@RestController
@RequestMapping("/api/assignments")
public class AssignmentResource {

    private final Logger log = LoggerFactory.getLogger(AssignmentResource.class);

    private static final String ENTITY_NAME = "assignments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssignmentService assignmentService;

    private final AssignmentRepository assignmentRepository;

    public AssignmentResource(AssignmentService assignmentService, AssignmentRepository assignmentRepository) {
        this.assignmentService = assignmentService;
        this.assignmentRepository = assignmentRepository;
    }

    /**
     * {@code POST  /assignments} : Create a new assignment.
     *
     * @param assignmentDTO the assessmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assessmentDTO, or with status {@code 400 (Bad Request)} if the assessment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(value = "", consumes = "multipart/form-data")
    public ResponseEntity<AssignmentDTO> createAssignment(
        @RequestPart("description") String description,
        @RequestPart("technology") String technology,
        @RequestPart("difficultyLevel") String difficultyLevel,
        @RequestPart("timeLimit") LocalTime timeLimit,
        @RequestPart("image") MultipartFile image,
        @RequestPart("assignmentType") CategoryDTO type,
        @RequestPart("question") String question,
        @RequestPart("maxPoints") Integer maxPoints,
        @RequestPart("evaluationType") String evaluationType
    ) throws URISyntaxException {
        log.debug("REST request to save Assignment");

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setDescription(description);
        assignmentDTO.setTechnology(technology);
        assignmentDTO.setDifficultyLevel(difficultyLevel);
        assignmentDTO.setTimeLimit(timeLimit);
        assignmentDTO.setType(type);
        assignmentDTO.setQuestion(question);
        assignmentDTO.setMaxPoints(maxPoints);
        assignmentDTO.setEvaluationType(evaluationType);

        try {
            byte[] imageBytes = image.getBytes();
            assignmentDTO.setImage(imageBytes);
        } catch (IOException e) {
            throw new BadRequestAlertException("Failed to process image file", ENTITY_NAME, "imageprocessingerror");
        }

        if (assignmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new assignment cannot already have an ID", ENTITY_NAME, "idexists");
        }

        AssignmentDTO result = assignmentService.save(assignmentDTO);

        return ResponseEntity
            .created(new URI("/api/assignments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /assignments/:id} : Updates an existing assessment.
     *
     * @param id the id of the assessmentDTO to save.
     * @param assignmentDTO the assessmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assessmentDTO,
     * or with status {@code 400 (Bad Request)} if the assessmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assessmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AssignmentDTO> updateAssignment(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody AssignmentDTO assignmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Assessment : {}, {}", id, assignmentDTO);
        if (assignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AssignmentDTO result = assignmentService.update(assignmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assignmentDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /assignments/:id} : Partial updates given fields of an existing assessment, field will ignore if it is null
     *
     * @param id the id of the assessmentDTO to save.
     * @param assignmentDTO the assessmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assessmentDTO,
     * or with status {@code 400 (Bad Request)} if the assessmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the assessmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the assessmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssignmentDTO> partialUpdateAssessment(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody AssignmentDTO assignmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Assessment partially : {}, {}", id, assignmentDTO);
        if (assignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssignmentDTO> result = assignmentService.partialUpdate(assignmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assignmentDTO.getId())
        );
    }

    /**
     * {@code GET  /assignments} : get all the assessments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assessments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AssignmentDTO>> getAllAssignments(
        @RequestParam(required = false) String type,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of Assignments");
        final Page<AssignmentDTO> page;
        if (type == null) {
            page = assignmentService.findAll(pageable);
        } else {
            page = assignmentService.findAssessmentsByType(type, pageable);
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /assignments/:id} : get the "id" assessment.
     *
     * @param id the id of the assessmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assessmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDTO> getAssignment(@PathVariable("id") String id) {
        log.debug("REST request to get Assessment : {}", id);
        Optional<AssignmentDTO> assignmentDTO = assignmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assignmentDTO);
    }

    /**
     * {@code DELETE  /assignments/:id} : delete the "id" assessment.
     *
     * @param id the id of the assessmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable("id") String id) {
        log.debug("REST request to delete Assessment : {}", id);
        assignmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
