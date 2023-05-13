import { ILicenseEast, NewLicenseEast } from './license-east.model';

export const sampleWithRequiredData: ILicenseEast = {
  id: 19691,
};

export const sampleWithPartialData: ILicenseEast = {
  id: 90689,
  licenseNumber: 51737,
};

export const sampleWithFullData: ILicenseEast = {
  id: 24256,
  licenseNumber: 28883,
  licenseDescription: 'Somalia Ruble',
};

export const sampleWithNewData: NewLicenseEast = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
