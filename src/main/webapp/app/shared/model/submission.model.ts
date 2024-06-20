import { IUserAssessment } from 'app/shared/model/user-assignment.model';
import { IUser } from 'app/shared/model/user.model';
import { IAssessment } from 'app/shared/model/assignment.model';

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
  assignment?: IAssessment | null;
}

export const defaultValue: Readonly<ISubmission> = {};
