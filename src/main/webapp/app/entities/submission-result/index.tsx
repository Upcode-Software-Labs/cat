import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SubmissionResult from './submission-result';
import SubmissionResultDetail from './submission-result-detail';
import SubmissionResultUpdate from './submission-result-update';
import SubmissionResultDeleteDialog from './submission-result-delete-dialog';

const SubmissionResultRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SubmissionResult />} />
    <Route path="new" element={<SubmissionResultUpdate />} />
    <Route path=":id">
      <Route index element={<SubmissionResultDetail />} />
      <Route path="edit" element={<SubmissionResultUpdate />} />
      <Route path="delete" element={<SubmissionResultDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SubmissionResultRoutes;
