import { ISubmission } from 'app/shared/model/submission.model';

export interface ISubmissionResult {
  id?: string;
  totalPoints?: number | null;
  detailedResults?: string | null;
  feedback?: string | null;
  submission?: ISubmission | null;
}

export const defaultValue: Readonly<ISubmissionResult> = {};
