package in.upcode.cat.service.dto;

public class DashboardDTO {

    private int assessments;
    private int submissions;

    public DashboardDTO(int assessments, int submissions) {
        this.assessments = assessments;
        this.submissions = submissions;
    }

    // Getters and setters

    public int getAssessments() {
        return assessments;
    }

    public void setAssessments(int assessments) {
        this.assessments = assessments;
    }

    public int getSubmissions() {
        return submissions;
    }

    public void setSubmissions(int submissions) {
        this.submissions = submissions;
    }
}
