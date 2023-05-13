import { IRailwayStationEast, NewRailwayStationEast } from './railway-station-east.model';

export const sampleWithRequiredData: IRailwayStationEast = {
  id: 9034,
};

export const sampleWithPartialData: IRailwayStationEast = {
  id: 56485,
  railwayTypeId: 57130,
  addressId: 18417,
};

export const sampleWithFullData: IRailwayStationEast = {
  id: 49549,
  railwayStationName: 'quantifying maroon Awesome',
  railwayTypeId: 87115,
  addressId: 82081,
};

export const sampleWithNewData: NewRailwayStationEast = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
