import { IUserTypeEast, NewUserTypeEast } from './user-type-east.model';

export const sampleWithRequiredData: IUserTypeEast = {
  id: 74127,
};

export const sampleWithPartialData: IUserTypeEast = {
  id: 90119,
};

export const sampleWithFullData: IUserTypeEast = {
  id: 98337,
  code: 'Towels deposit',
  discount: 58859,
};

export const sampleWithNewData: NewUserTypeEast = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
