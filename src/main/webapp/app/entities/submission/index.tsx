import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Submission from './submission';
import SubmissionDetail from './submission-detail';
import SubmissionUpdate from './submission-update';
import SubmissionDeleteDialog from './submission-delete-dialog';

const SubmissionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Submission />} />
    <Route path="new" element={<SubmissionUpdate />} />
    <Route path=":id">
      <Route index element={<SubmissionDetail />} />
      <Route path="edit" element={<SubmissionUpdate />} />
      <Route path="delete" element={<SubmissionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SubmissionRoutes;
