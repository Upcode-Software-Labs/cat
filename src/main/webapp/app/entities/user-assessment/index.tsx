import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserAssessment from './user-assignment';
import UserAssessmentDetail from './user-assignment-detail';
import UserAssessmentUpdate from './user-assignment-update';
import UserAssessmentDeleteDialog from './user-assignment-delete-dialog';

const UserAssessmentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserAssessment />} />
    <Route path="new" element={<UserAssessmentUpdate />} />
    <Route path=":id">
      <Route index element={<UserAssessmentDetail />} />
      <Route path="edit" element={<UserAssessmentUpdate />} />
      <Route path="delete" element={<UserAssessmentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserAssessmentRoutes;
