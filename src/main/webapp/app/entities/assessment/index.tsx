import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Assessment from './assessment';
import AssessmentDetail from './assessment-detail';
import AssessmentUpdate from './assessment-update';
import AssessmentDeleteDialog from './assessment-delete-dialog';

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
