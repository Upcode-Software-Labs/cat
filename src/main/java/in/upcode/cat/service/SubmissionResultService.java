package in.upcode.cat.service;

import in.upcode.cat.domain.SubmissionResult;
import in.upcode.cat.repository.SubmissionResultRepository;
import in.upcode.cat.service.dto.SubmissionResultDTO;
import in.upcode.cat.service.mapper.SubmissionResultMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link in.upcode.cat.domain.SubmissionResult}.
 */
@Service
public class SubmissionResultService {

    private final Logger log = LoggerFactory.getLogger(SubmissionResultService.class);

    private final SubmissionResultRepository submissionResultRepository;

    private final SubmissionResultMapper submissionResultMapper;

    public SubmissionResultService(SubmissionResultRepository submissionResultRepository, SubmissionResultMapper submissionResultMapper) {
        this.submissionResultRepository = submissionResultRepository;
        this.submissionResultMapper = submissionResultMapper;
    }

    /**
     * Save a submissionResult.
     *
     * @param submissionResultDTO the entity to save.
     * @return the persisted entity.
     */
    public SubmissionResultDTO save(SubmissionResultDTO submissionResultDTO) {
        log.debug("Request to save SubmissionResult : {}", submissionResultDTO);
        SubmissionResult submissionResult = submissionResultMapper.toEntity(submissionResultDTO);
        submissionResult = submissionResultRepository.save(submissionResult);
        return submissionResultMapper.toDto(submissionResult);
    }

    /**
     * Update a submissionResult.
     *
     * @param submissionResultDTO the entity to save.
     * @return the persisted entity.
     */
    public SubmissionResultDTO update(SubmissionResultDTO submissionResultDTO) {
        log.debug("Request to update SubmissionResult : {}", submissionResultDTO);
        SubmissionResult submissionResult = submissionResultMapper.toEntity(submissionResultDTO);
        submissionResult = submissionResultRepository.save(submissionResult);
        return submissionResultMapper.toDto(submissionResult);
    }

    /**
     * Partially update a submissionResult.
     *
     * @param submissionResultDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubmissionResultDTO> partialUpdate(SubmissionResultDTO submissionResultDTO) {
        log.debug("Request to partially update SubmissionResult : {}", submissionResultDTO);

        return submissionResultRepository
            .findById(submissionResultDTO.getId())
            .map(existingSubmissionResult -> {
                submissionResultMapper.partialUpdate(existingSubmissionResult, submissionResultDTO);

                return existingSubmissionResult;
            })
            .map(submissionResultRepository::save)
            .map(submissionResultMapper::toDto);
    }

    /**
     * Get all the submissionResults.
     *
     * @return the list of entities.
     */
    public List<SubmissionResultDTO> findAll() {
        log.debug("Request to get all SubmissionResults");
        return submissionResultRepository
            .findAll()
            .stream()
            .map(submissionResultMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one submissionResult by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SubmissionResultDTO> findOne(String id) {
        log.debug("Request to get SubmissionResult : {}", id);
        return submissionResultRepository.findById(id).map(submissionResultMapper::toDto);
    }

    /**
     * Delete the submissionResult by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete SubmissionResult : {}", id);
        submissionResultRepository.deleteById(id);
    }
}
