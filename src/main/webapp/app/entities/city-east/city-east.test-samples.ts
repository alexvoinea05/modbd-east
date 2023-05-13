import { ICityEast, NewCityEast } from './city-east.model';

export const sampleWithRequiredData: ICityEast = {
  id: 45315,
};

export const sampleWithPartialData: ICityEast = {
  id: 11859,
  name: 'Auto',
  districtId: 35771,
};

export const sampleWithFullData: ICityEast = {
  id: 29270,
  name: 'Chicken',
  districtId: 89480,
};

export const sampleWithNewData: NewCityEast = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
