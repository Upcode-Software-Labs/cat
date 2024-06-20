import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Assessment from './assignment';
import UserAssessment from './user-assignment';
import Submission from './submission';
import Question from './question';
import ValidationRule from './validation-rule';
import SubmissionResult from './submission-result';
import AuditLog from './audit-log';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="assignment/*" element={<Assessment />} />
        <Route path="user-assignment/*" element={<UserAssessment />} />
        <Route path="submission/*" element={<Submission />} />
        <Route path="question/*" element={<Question />} />
        <Route path="validation-rule/*" element={<ValidationRule />} />
        <Route path="submission-result/*" element={<SubmissionResult />} />
        <Route path="audit-log/*" element={<AuditLog />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
