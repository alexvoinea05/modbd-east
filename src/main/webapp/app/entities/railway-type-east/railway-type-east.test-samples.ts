import { IRailwayTypeEast, NewRailwayTypeEast } from './railway-type-east.model';

export const sampleWithRequiredData: IRailwayTypeEast = {
  id: 93009,
};

export const sampleWithPartialData: IRailwayTypeEast = {
  id: 75241,
};

export const sampleWithFullData: IRailwayTypeEast = {
  id: 16459,
  code: 'bypassing compress',
  description: 'compressing',
};

export const sampleWithNewData: NewRailwayTypeEast = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
