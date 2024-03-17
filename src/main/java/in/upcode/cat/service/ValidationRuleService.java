package in.upcode.cat.service;

import in.upcode.cat.domain.ValidationRule;
import in.upcode.cat.repository.ValidationRuleRepository;
import in.upcode.cat.service.dto.ValidationRuleDTO;
import in.upcode.cat.service.mapper.ValidationRuleMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link in.upcode.cat.domain.ValidationRule}.
 */
@Service
public class ValidationRuleService {

    private final Logger log = LoggerFactory.getLogger(ValidationRuleService.class);

    private final ValidationRuleRepository validationRuleRepository;

    private final ValidationRuleMapper validationRuleMapper;

    public ValidationRuleService(ValidationRuleRepository validationRuleRepository, ValidationRuleMapper validationRuleMapper) {
        this.validationRuleRepository = validationRuleRepository;
        this.validationRuleMapper = validationRuleMapper;
    }

    /**
     * Save a validationRule.
     *
     * @param validationRuleDTO the entity to save.
     * @return the persisted entity.
     */
    public ValidationRuleDTO save(ValidationRuleDTO validationRuleDTO) {
        log.debug("Request to save ValidationRule : {}", validationRuleDTO);
        ValidationRule validationRule = validationRuleMapper.toEntity(validationRuleDTO);
        validationRule = validationRuleRepository.save(validationRule);
        return validationRuleMapper.toDto(validationRule);
    }

    /**
     * Update a validationRule.
     *
     * @param validationRuleDTO the entity to save.
     * @return the persisted entity.
     */
    public ValidationRuleDTO update(ValidationRuleDTO validationRuleDTO) {
        log.debug("Request to update ValidationRule : {}", validationRuleDTO);
        ValidationRule validationRule = validationRuleMapper.toEntity(validationRuleDTO);
        validationRule = validationRuleRepository.save(validationRule);
        return validationRuleMapper.toDto(validationRule);
    }

    /**
     * Partially update a validationRule.
     *
     * @param validationRuleDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ValidationRuleDTO> partialUpdate(ValidationRuleDTO validationRuleDTO) {
        log.debug("Request to partially update ValidationRule : {}", validationRuleDTO);

        return validationRuleRepository
            .findById(validationRuleDTO.getId())
            .map(existingValidationRule -> {
                validationRuleMapper.partialUpdate(existingValidationRule, validationRuleDTO);

                return existingValidationRule;
            })
            .map(validationRuleRepository::save)
            .map(validationRuleMapper::toDto);
    }

    /**
     * Get all the validationRules.
     *
     * @return the list of entities.
     */
    public List<ValidationRuleDTO> findAll() {
        log.debug("Request to get all ValidationRules");
        return validationRuleRepository
            .findAll()
            .stream()
            .map(validationRuleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one validationRule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ValidationRuleDTO> findOne(String id) {
        log.debug("Request to get ValidationRule : {}", id);
        return validationRuleRepository.findById(id).map(validationRuleMapper::toDto);
    }

    /**
     * Delete the validationRule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete ValidationRule : {}", id);
        validationRuleRepository.deleteById(id);
    }
}
