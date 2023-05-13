import dayjs from 'dayjs/esm';

export interface ITicketEast {
  id: number;
  finalPrice?: number | null;
  quantity?: number | null;
  time?: dayjs.Dayjs | null;
  appUserId?: number | null;
  journeyId?: number | null;
}

export type NewTicketEast = Omit<ITicketEast, 'id'> & { id: null };
