import { IAssessment } from 'app/shared/model/assessment.model';

export interface IValidationRule {
  id?: string;
  description?: string;
  validationScript?: string | null;
  ruleType?: string;
  assessment?: IAssessment | null;
}

export const defaultValue: Readonly<IValidationRule> = {};
