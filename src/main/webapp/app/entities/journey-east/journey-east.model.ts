import dayjs from 'dayjs/esm';

export interface IJourneyEast {
  id: number;
  distance?: number | null;
  journeyDuration?: number | null;
  actualDepartureTime?: dayjs.Dayjs | null;
  plannedDepartureTime?: dayjs.Dayjs | null;
  actualArrivalTime?: dayjs.Dayjs | null;
  plannedArrivalTime?: dayjs.Dayjs | null;
  ticketPrice?: number | null;
  numberOfStops?: number | null;
  timeOfStops?: number | null;
  minutesLate?: number | null;
  journeyStatusId?: number | null;
  trainId?: number | null;
  companyId?: number | null;
  departureRailwayStationId?: number | null;
  arivalRailwayStationId?: number | null;
}

export type NewJourneyEast = Omit<IJourneyEast, 'id'> & { id: null };
