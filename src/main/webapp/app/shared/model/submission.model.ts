import { IUserAssessment } from 'app/shared/model/user-assessment.model';
import { IUser } from 'app/shared/model/user.model';
import { IAssessment } from 'app/shared/model/assessment.model';

export interface ISubmission {
  id?: string;
  githubUrl?: string;
  screenshotsContentType?: string | null;
  screenshots?: string | null;
  videoExplanation?: string | null;
  textDescription?: string | null;
  feedback?: string | null;
  pointsScored?: number | null;
  forAssignment?: IUserAssessment | null;
  user?: IUser | null;
  assessment?: IAssessment | null;
}

export const defaultValue: Readonly<ISubmission> = {};
