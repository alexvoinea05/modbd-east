import dayjs from 'dayjs/esm';

import { IJourneyEast, NewJourneyEast } from './journey-east.model';

export const sampleWithRequiredData: IJourneyEast = {
  id: 83231,
};

export const sampleWithPartialData: IJourneyEast = {
  id: 67052,
  distance: 5994,
  journeyDuration: 55321,
  plannedDepartureTime: dayjs('2023-05-13T10:29'),
  actualArrivalTime: dayjs('2023-05-13T11:14'),
  ticketPrice: 90327,
  timeOfStops: 48879,
  minutesLate: 63570,
  companyId: 47458,
};

export const sampleWithFullData: IJourneyEast = {
  id: 43330,
  distance: 6924,
  journeyDuration: 48680,
  actualDepartureTime: dayjs('2023-05-13T05:43'),
  plannedDepartureTime: dayjs('2023-05-12T21:19'),
  actualArrivalTime: dayjs('2023-05-12T18:47'),
  plannedArrivalTime: dayjs('2023-05-13T07:00'),
  ticketPrice: 52747,
  numberOfStops: 7022,
  timeOfStops: 23608,
  minutesLate: 90271,
  journeyStatusId: 60525,
  trainId: 89726,
  companyId: 29417,
  departureRailwayStationId: 67863,
  arivalRailwayStationId: 89244,
};

export const sampleWithNewData: NewJourneyEast = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
