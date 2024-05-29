package in.upcode.cat.web.rest;

import in.upcode.cat.repository.AssignmentRepository;
import in.upcode.cat.repository.SubmissionRepository;
import in.upcode.cat.service.dto.DashboardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminDasboardResource {

    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;

    @Autowired
    public AdminDasboardResource(AssignmentRepository assignmentRepository, SubmissionRepository submissionRepository) {
        this.assignmentRepository = assignmentRepository;
        this.submissionRepository = submissionRepository;
    }

    @GetMapping("/dashboard")
    public DashboardDTO getDashboardMetrics() {
        long totalAssessments = assignmentRepository.count();
        long totalSubmissions = submissionRepository.count();

        return new DashboardDTO((int) totalAssessments, (int) totalSubmissions);
    }
}
