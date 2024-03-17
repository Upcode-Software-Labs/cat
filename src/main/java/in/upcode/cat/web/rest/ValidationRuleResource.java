package in.upcode.cat.web.rest;

import in.upcode.cat.repository.ValidationRuleRepository;
import in.upcode.cat.service.ValidationRuleService;
import in.upcode.cat.service.dto.ValidationRuleDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link in.upcode.cat.domain.ValidationRule}.
 */
@RestController
@RequestMapping("/api/validation-rules")
public class ValidationRuleResource {

    private final Logger log = LoggerFactory.getLogger(ValidationRuleResource.class);

    private static final String ENTITY_NAME = "validationRule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ValidationRuleService validationRuleService;

    private final ValidationRuleRepository validationRuleRepository;

    public ValidationRuleResource(ValidationRuleService validationRuleService, ValidationRuleRepository validationRuleRepository) {
        this.validationRuleService = validationRuleService;
        this.validationRuleRepository = validationRuleRepository;
    }

    /**
     * {@code POST  /validation-rules} : Create a new validationRule.
     *
     * @param validationRuleDTO the validationRuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new validationRuleDTO, or with status {@code 400 (Bad Request)} if the validationRule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ValidationRuleDTO> createValidationRule(@Valid @RequestBody ValidationRuleDTO validationRuleDTO)
        throws URISyntaxException {
        log.debug("REST request to save ValidationRule : {}", validationRuleDTO);
        if (validationRuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new validationRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ValidationRuleDTO result = validationRuleService.save(validationRuleDTO);
        return ResponseEntity
            .created(new URI("/api/validation-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /validation-rules/:id} : Updates an existing validationRule.
     *
     * @param id the id of the validationRuleDTO to save.
     * @param validationRuleDTO the validationRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated validationRuleDTO,
     * or with status {@code 400 (Bad Request)} if the validationRuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the validationRuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ValidationRuleDTO> updateValidationRule(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ValidationRuleDTO validationRuleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ValidationRule : {}, {}", id, validationRuleDTO);
        if (validationRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, validationRuleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!validationRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ValidationRuleDTO result = validationRuleService.update(validationRuleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, validationRuleDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /validation-rules/:id} : Partial updates given fields of an existing validationRule, field will ignore if it is null
     *
     * @param id the id of the validationRuleDTO to save.
     * @param validationRuleDTO the validationRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated validationRuleDTO,
     * or with status {@code 400 (Bad Request)} if the validationRuleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the validationRuleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the validationRuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ValidationRuleDTO> partialUpdateValidationRule(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ValidationRuleDTO validationRuleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ValidationRule partially : {}, {}", id, validationRuleDTO);
        if (validationRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, validationRuleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!validationRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ValidationRuleDTO> result = validationRuleService.partialUpdate(validationRuleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, validationRuleDTO.getId())
        );
    }

    /**
     * {@code GET  /validation-rules} : get all the validationRules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of validationRules in body.
     */
    @GetMapping("")
    public List<ValidationRuleDTO> getAllValidationRules() {
        log.debug("REST request to get all ValidationRules");
        return validationRuleService.findAll();
    }

    /**
     * {@code GET  /validation-rules/:id} : get the "id" validationRule.
     *
     * @param id the id of the validationRuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the validationRuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ValidationRuleDTO> getValidationRule(@PathVariable("id") String id) {
        log.debug("REST request to get ValidationRule : {}", id);
        Optional<ValidationRuleDTO> validationRuleDTO = validationRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(validationRuleDTO);
    }

    /**
     * {@code DELETE  /validation-rules/:id} : delete the "id" validationRule.
     *
     * @param id the id of the validationRuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValidationRule(@PathVariable("id") String id) {
        log.debug("REST request to delete ValidationRule : {}", id);
        validationRuleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
