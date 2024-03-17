import { IAssessment } from 'app/shared/model/assessment.model';

export interface IQuestion {
  id?: string;
  questionText?: string;
  codeSnippet?: string | null;
  resources?: string | null;
  points?: number | null;
  assessment?: IAssessment | null;
}

export const defaultValue: Readonly<IQuestion> = {};
