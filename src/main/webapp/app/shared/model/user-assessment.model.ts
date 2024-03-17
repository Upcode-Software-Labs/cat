import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IAssessment } from 'app/shared/model/assessment.model';
import { AssessmentStatus } from 'app/shared/model/enumerations/assessment-status.model';

export interface IUserAssessment {
  id?: string;
  status?: keyof typeof AssessmentStatus;
  assignedAt?: dayjs.Dayjs;
  deadline?: dayjs.Dayjs | null;
  submittedByUser?: IUser | null;
  user?: IUser | null;
  assessment?: IAssessment | null;
}

export const defaultValue: Readonly<IUserAssessment> = {};
