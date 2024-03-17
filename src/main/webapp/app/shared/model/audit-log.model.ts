import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IAuditLog {
  id?: string;
  action?: string;
  performedAt?: dayjs.Dayjs;
  details?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IAuditLog> = {};
