import { IAssessment } from 'app/shared/model/assignment.model';

export interface IValidationRule {
  id?: string;
  description?: string;
  validationScript?: string | null;
  ruleType?: string;
  assignment?: IAssessment | null;
}

export const defaultValue: Readonly<IValidationRule> = {};
