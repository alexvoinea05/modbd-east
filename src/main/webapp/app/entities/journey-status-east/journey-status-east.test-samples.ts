import { IJourneyStatusEast, NewJourneyStatusEast } from './journey-status-east.model';

export const sampleWithRequiredData: IJourneyStatusEast = {
  id: 61987,
};

export const sampleWithPartialData: IJourneyStatusEast = {
  id: 9927,
  code: '1080p open-source',
};

export const sampleWithFullData: IJourneyStatusEast = {
  id: 59163,
  code: 'wireless',
  description: 'copy',
};

export const sampleWithNewData: NewJourneyStatusEast = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
