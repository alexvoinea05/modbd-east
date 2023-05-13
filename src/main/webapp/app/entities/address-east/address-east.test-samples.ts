import { IAddressEast, NewAddressEast } from './address-east.model';

export const sampleWithRequiredData: IAddressEast = {
  id: 60819,
};

export const sampleWithPartialData: IAddressEast = {
  id: 76350,
  street: 'Miracle Pass',
};

export const sampleWithFullData: IAddressEast = {
  id: 89016,
  streetNumber: 'Officer',
  street: 'Douglas Views',
  zipcode: 'Computers Kids',
  cityId: 70578,
};

export const sampleWithNewData: NewAddressEast = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
