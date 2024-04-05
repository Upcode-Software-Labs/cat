package in.upcode.cat.service;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.FileSetCheck;
import in.upcode.cat.domain.Submission;
import in.upcode.cat.repository.SubmissionRepository;
import in.upcode.cat.service.dto.SubmissionDTO;
import in.upcode.cat.service.mapper.SubmissionMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link in.upcode.cat.domain.Submission}.
 */
@Service
public class SubmissionService {

    private final Logger log = LoggerFactory.getLogger(SubmissionService.class);

    private final SubmissionRepository submissionRepository;

    private final SubmissionMapper submissionMapper;

    public SubmissionService(SubmissionRepository submissionRepository, SubmissionMapper submissionMapper) {
        this.submissionRepository = submissionRepository;
        this.submissionMapper = submissionMapper;
    }

    /**
     * Save a submission.
     *
     * @param submissionDTO the entity to save.
     * @return the persisted entity.
     */
    public SubmissionDTO save(SubmissionDTO submissionDTO) {
        log.debug("Request to save Submission : {}", submissionDTO);
        Submission submission = submissionMapper.toEntity(submissionDTO);
        submission = submissionRepository.save(submission);
        return submissionMapper.toDto(submission);
    }

    /**
     * Save a submission.
     *
     * @param submissionDTO the entity to save.
     * @return the persisted entity.
     */
    public SubmissionDTO saveSubmit(SubmissionDTO submissionDTO)
        throws URISyntaxException, IOException, InterruptedException, CheckstyleException {
        log.debug("Request to save Submission : {}", submissionDTO);

        String githubUrl = submissionDTO.getGithubUrl();

        // Remove "/blob/"
        String rawUrl = githubUrl.replace("/blob/", "/");

        // Replace "github.com" with "raw.githubusercontent.com"
        rawUrl = rawUrl.replace("github.com", "raw.githubusercontent.com");

        String rawContent = SubmissionSubmitService.getRawContent(rawUrl);

        log.debug("File contents : {}", rawContent);

        // Display the result using logger.debug
        log.debug("####Checkstyle Result: {}", SubmissionSubmitService.codeCheck(rawContent));
        submissionDTO.setResults(SubmissionSubmitService.codeCheck(rawContent));

        Submission submission = submissionMapper.toEntity(submissionDTO);
        submission = submissionRepository.save(submission);
        return submissionMapper.toDto(submission);
    }

    private static File createTempFile(String content) throws IOException {
        File tempFile = File.createTempFile("tempJavaFile", ".java");

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(content);
        }

        return tempFile;
    }

    /**
     * Update a submission.
     *
     * @param submissionDTO the entity to save.
     * @return the persisted entity.
     */
    public SubmissionDTO update(SubmissionDTO submissionDTO) {
        log.debug("Request to update Submission : {}", submissionDTO);
        Submission submission = submissionMapper.toEntity(submissionDTO);
        submission = submissionRepository.save(submission);
        return submissionMapper.toDto(submission);
    }

    /**
     * Partially update a submission.
     *
     * @param submissionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubmissionDTO> partialUpdate(SubmissionDTO submissionDTO) {
        log.debug("Request to partially update Submission : {}", submissionDTO);

        return submissionRepository
            .findById(submissionDTO.getId())
            .map(existingSubmission -> {
                submissionMapper.partialUpdate(existingSubmission, submissionDTO);

                return existingSubmission;
            })
            .map(submissionRepository::save)
            .map(submissionMapper::toDto);
    }

    /**
     * Get all the submissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SubmissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Submissions");
        return submissionRepository.findAll(pageable).map(submissionMapper::toDto);
    }

    /**
     * Get one submission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SubmissionDTO> findOne(String id) {
        log.debug("Request to get Submission : {}", id);
        return submissionRepository.findById(id).map(submissionMapper::toDto);
    }

    /**
     * Delete the submission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Submission : {}", id);
        submissionRepository.deleteById(id);
    }
}
