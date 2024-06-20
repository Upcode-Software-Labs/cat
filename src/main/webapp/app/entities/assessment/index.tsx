import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Assessment from './assignment';
import AssessmentDetail from './assignment-detail';
import AssessmentUpdate from './assignment-update';
import AssessmentDeleteDialog from './assignment-delete-dialog';

const AssessmentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Assessment />} />
    <Route path="new" element={<AssessmentUpdate />} />
    <Route path=":id">
      <Route index element={<AssessmentDetail />} />
      <Route path="edit" element={<AssessmentUpdate />} />
      <Route path="delete" element={<AssessmentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AssessmentRoutes;
