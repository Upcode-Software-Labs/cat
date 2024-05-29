import { IAssessment } from 'app/shared/model/assignment.model';

export interface IQuestion {
  id?: string;
  questionText?: string;
  codeSnippet?: string | null;
  resources?: string | null;
  points?: number | null;
  assignment?: IAssessment | null;
}

export const defaultValue: Readonly<IQuestion> = {};
