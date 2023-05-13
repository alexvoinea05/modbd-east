import { ICompanyEast, NewCompanyEast } from './company-east.model';

export const sampleWithRequiredData: ICompanyEast = {
  id: 54006,
};

export const sampleWithPartialData: ICompanyEast = {
  id: 86315,
  identificationNumber: 'Dynamic Buckinghamshire Samoa',
};

export const sampleWithFullData: ICompanyEast = {
  id: 23389,
  name: 'Tasty Refined 24/365',
  identificationNumber: 'Implemented indigo',
};

export const sampleWithNewData: NewCompanyEast = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
