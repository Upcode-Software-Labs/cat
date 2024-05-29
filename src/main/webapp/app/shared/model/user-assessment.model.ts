import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IAssessment } from 'app/shared/model/assignment.model';
import { AssessmentStatus } from 'app/shared/model/enumerations/assignment-status.model';

export interface IUserAssessment {
  id?: string;
  status?: keyof typeof AssessmentStatus;
  assignedAt?: dayjs.Dayjs;
  deadline?: dayjs.Dayjs | null;
  submittedByUser?: IUser | null;
  user?: IUser | null;
  assignment?: IAssessment | null;
}

export const defaultValue: Readonly<IUserAssessment> = {};
