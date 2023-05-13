import { IDistrictEast, NewDistrictEast } from './district-east.model';

export const sampleWithRequiredData: IDistrictEast = {
  id: 86938,
};

export const sampleWithPartialData: IDistrictEast = {
  id: 81387,
  name: 'payment drive',
  region: 'Macedonia Wooden',
};

export const sampleWithFullData: IDistrictEast = {
  id: 92798,
  name: 'Bedfordshire neural Future',
  region: 'Massachusetts Program',
};

export const sampleWithNewData: NewDistrictEast = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
