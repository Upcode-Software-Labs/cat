import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IAssessment {
  id?: string;
  title?: string;
  description?: string | null;
  languageFramework?: string | null;
  difficultyLevel?: string | null;
  timeLimit?: number | null;
  type?: string;
  validationCriteria?: string;
  question?: string;
  maxPoints?: number;
  deadline?: dayjs.Dayjs;
  assignedToUser?: IUser | null;
}

export const defaultValue: Readonly<IAssessment> = {};
