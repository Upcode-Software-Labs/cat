package in.upcode.cat.web.rest;

import in.upcode.cat.repository.SubmissionResultRepository;
import in.upcode.cat.service.SubmissionResultService;
import in.upcode.cat.service.dto.SubmissionResultDTO;
import in.upcode.cat.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link in.upcode.cat.domain.SubmissionResult}.
 */
@RestController
@RequestMapping("/api/submission-results")
public class SubmissionResultResource {

    private final Logger log = LoggerFactory.getLogger(SubmissionResultResource.class);

    private static final String ENTITY_NAME = "submissionResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubmissionResultService submissionResultService;

    private final SubmissionResultRepository submissionResultRepository;

    public SubmissionResultResource(
        SubmissionResultService submissionResultService,
        SubmissionResultRepository submissionResultRepository
    ) {
        this.submissionResultService = submissionResultService;
        this.submissionResultRepository = submissionResultRepository;
    }

    /**
     * {@code POST  /submission-results} : Create a new submissionResult.
     *
     * @param submissionResultDTO the submissionResultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new submissionResultDTO, or with status {@code 400 (Bad Request)} if the submissionResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SubmissionResultDTO> createSubmissionResult(@RequestBody SubmissionResultDTO submissionResultDTO)
        throws URISyntaxException {
        log.debug("REST request to save SubmissionResult : {}", submissionResultDTO);
        if (submissionResultDTO.getId() != null) {
            throw new BadRequestAlertException("A new submissionResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubmissionResultDTO result = submissionResultService.save(submissionResultDTO);
        return ResponseEntity
            .created(new URI("/api/submission-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /submission-results/:id} : Updates an existing submissionResult.
     *
     * @param id the id of the submissionResultDTO to save.
     * @param submissionResultDTO the submissionResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated submissionResultDTO,
     * or with status {@code 400 (Bad Request)} if the submissionResultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the submissionResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SubmissionResultDTO> updateSubmissionResult(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SubmissionResultDTO submissionResultDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SubmissionResult : {}, {}", id, submissionResultDTO);
        if (submissionResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, submissionResultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!submissionResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubmissionResultDTO result = submissionResultService.update(submissionResultDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, submissionResultDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /submission-results/:id} : Partial updates given fields of an existing submissionResult, field will ignore if it is null
     *
     * @param id the id of the submissionResultDTO to save.
     * @param submissionResultDTO the submissionResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated submissionResultDTO,
     * or with status {@code 400 (Bad Request)} if the submissionResultDTO is not valid,
     * or with status {@code 404 (Not Found)} if the submissionResultDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the submissionResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubmissionResultDTO> partialUpdateSubmissionResult(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SubmissionResultDTO submissionResultDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubmissionResult partially : {}, {}", id, submissionResultDTO);
        if (submissionResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, submissionResultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!submissionResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubmissionResultDTO> result = submissionResultService.partialUpdate(submissionResultDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, submissionResultDTO.getId())
        );
    }

    /**
     * {@code GET  /submission-results} : get all the submissionResults.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of submissionResults in body.
     */
    @GetMapping("")
    public List<SubmissionResultDTO> getAllSubmissionResults() {
        log.debug("REST request to get all SubmissionResults");
        return submissionResultService.findAll();
    }

    /**
     * {@code GET  /submission-results/:id} : get the "id" submissionResult.
     *
     * @param id the id of the submissionResultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the submissionResultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SubmissionResultDTO> getSubmissionResult(@PathVariable("id") String id) {
        log.debug("REST request to get SubmissionResult : {}", id);
        Optional<SubmissionResultDTO> submissionResultDTO = submissionResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(submissionResultDTO);
    }

    /**
     * {@code DELETE  /submission-results/:id} : delete the "id" submissionResult.
     *
     * @param id the id of the submissionResultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubmissionResult(@PathVariable("id") String id) {
        log.debug("REST request to delete SubmissionResult : {}", id);
        submissionResultService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
