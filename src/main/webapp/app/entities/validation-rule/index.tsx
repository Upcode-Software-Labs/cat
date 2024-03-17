import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ValidationRule from './validation-rule';
import ValidationRuleDetail from './validation-rule-detail';
import ValidationRuleUpdate from './validation-rule-update';
import ValidationRuleDeleteDialog from './validation-rule-delete-dialog';

const ValidationRuleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ValidationRule />} />
    <Route path="new" element={<ValidationRuleUpdate />} />
    <Route path=":id">
      <Route index element={<ValidationRuleDetail />} />
      <Route path="edit" element={<ValidationRuleUpdate />} />
      <Route path="delete" element={<ValidationRuleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ValidationRuleRoutes;
