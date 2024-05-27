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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
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

        String rawContent = SubmissionCodeQualityCheck.getRawContent(rawUrl);

        final String[] parts = rawContent.split("/");

        final String fileName = parts[parts.length - 1];

        log.debug("File contents : {}", rawContent);

        // Display the result using logger.debug
        log.debug("####Checkstyle Result: {}", SubmissionCodeQualityCheck.codeCheck(fileName, rawContent));
        // submissionDTO.setResults(SubmissionCodeQualityCheck.codeCheck(fileName, rawContent));

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
    public SubmissionDTO checkQuality(SubmissionDTO submissionDTO) throws Exception {
        log.debug("Request to save Submission afet code Quality check : {}", submissionDTO);

        final String githubUrl = submissionDTO.getGithubUrl();

        //connect to github
        GitHub gitHub = GitHub.connectAnonymously();

        final String[] parts = SubmissionCodeQualityCheck.extractUsernameAndRepo(githubUrl);

        //get the public repo
        GHRepository repo = gitHub.getRepository(parts[0] + "/" + parts[1]);

        // List all files in the repository
        List<GHContent> contents = repo.getDirectoryContent("");

        final List<String> results = new ArrayList<>(); // Initialize an empty list to hold the results

        // Perform Checkstyle analysis for each file
        for (GHContent content : contents) {
            if (!content.isDirectory() && content.getName().endsWith(".java")) {
                final String fileName = content.getName();
                final String fileContent = content.getContent();

                log.debug("List of Files {}", fileName);

                // Perform Checkstyle analysis for the file
                final String report = SubmissionCodeQualityCheck.analyzeJavaFile(fileName, fileContent);

                // Append the result to the list
                results.add(fileName + ": " + report);

                // Print quality report
                log.info("Quality Report for: {}", fileName);
                log.info(results.toString());
            }
            // submissionDTO.setResults(results.toString());
        }

        Submission submission = submissionMapper.toEntity(submissionDTO);
        submission = submissionRepository.save(submission);
        return submissionMapper.toDto(submission);
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
     * Get the submissions based on search.
     *
     * @param pageable the pagination information.
     * @param type the name of student

     * @return the list of entities.
     */
    public Page<SubmissionDTO> findByAssessmentId(String type, Pageable pageable) {
        return submissionRepository.findByAssessment_Id(type, pageable).map(submissionMapper::toDto);
    }

    /**
     * Get the submissions based on search.
     *
     * @param pageable the pagination information.
     * @param user the name of student

     * @return the list of entities.
     */

    public Page<SubmissionDTO> findByUserId(String user, Pageable pageable) {
        return submissionRepository.findByUser_Id(user, pageable).map(submissionMapper::toDto);
    }

    public Page<SubmissionDTO> findByUserIdAndAssessmentId(String userId, String assessmentId, Pageable pageable) {
        return submissionRepository.findByUserIdAndAssessmentId(userId, assessmentId, pageable).map(submissionMapper::toDto);
    }

    /**
     * Get the submissions based on search.
     *
     * @param pageable the pagination information.
     * @param status the name of student

     * @return the list of entities.
     */
    public Page<SubmissionDTO> findByForAssessmentId(String status, Pageable pageable) {
        return submissionRepository.findByForAssignment_Id(status, pageable);
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
