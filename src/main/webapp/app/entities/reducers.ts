import assignment from 'app/entities/assignment/assignment.reducer';
import userAssignment from 'app/entities/user-assignment/user-assignment.reducer';
import submission from 'app/entities/submission/submission.reducer';
import question from 'app/entities/question/question.reducer';
import validationRule from 'app/entities/validation-rule/validation-rule.reducer';
import submissionResult from 'app/entities/submission-result/submission-result.reducer';
import auditLog from 'app/entities/audit-log/audit-log.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  assignment,
  userAssignment,
  submission,
  question,
  validationRule,
  submissionResult,
  auditLog,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
