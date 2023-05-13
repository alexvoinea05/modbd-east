import dayjs from 'dayjs/esm';

import { ITicketEast, NewTicketEast } from './ticket-east.model';

export const sampleWithRequiredData: ITicketEast = {
  id: 68309,
};

export const sampleWithPartialData: ITicketEast = {
  id: 89651,
  finalPrice: 39070,
  quantity: 50969,
};

export const sampleWithFullData: ITicketEast = {
  id: 1753,
  finalPrice: 53882,
  quantity: 857,
  time: dayjs('2023-05-13T05:44'),
  appUserId: 78976,
  journeyId: 37700,
};

export const sampleWithNewData: NewTicketEast = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
