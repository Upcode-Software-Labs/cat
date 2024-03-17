import assessment from 'app/entities/assessment/assessment.reducer';
import userAssessment from 'app/entities/user-assessment/user-assessment.reducer';
import submission from 'app/entities/submission/submission.reducer';
import question from 'app/entities/question/question.reducer';
import validationRule from 'app/entities/validation-rule/validation-rule.reducer';
import submissionResult from 'app/entities/submission-result/submission-result.reducer';
import auditLog from 'app/entities/audit-log/audit-log.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  assessment,
  userAssessment,
  submission,
  question,
  validationRule,
  submissionResult,
  auditLog,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
