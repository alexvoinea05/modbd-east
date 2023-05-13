import { IFuelTypeEast, NewFuelTypeEast } from './fuel-type-east.model';

export const sampleWithRequiredData: IFuelTypeEast = {
  id: 55850,
};

export const sampleWithPartialData: IFuelTypeEast = {
  id: 69753,
  code: 'Metal',
  description: 'end-to-end Refined Account',
};

export const sampleWithFullData: IFuelTypeEast = {
  id: 94953,
  code: 'Dakota',
  description: 'Cambridgeshire',
};

export const sampleWithNewData: NewFuelTypeEast = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
