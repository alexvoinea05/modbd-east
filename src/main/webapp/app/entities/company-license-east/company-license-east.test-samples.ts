import { ICompanyLicenseEast, NewCompanyLicenseEast } from './company-license-east.model';

export const sampleWithRequiredData: ICompanyLicenseEast = {
  id: 56039,
};

export const sampleWithPartialData: ICompanyLicenseEast = {
  id: 67187,
};

export const sampleWithFullData: ICompanyLicenseEast = {
  id: 53637,
};

export const sampleWithNewData: NewCompanyLicenseEast = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
