import { ITrainTypeEast, NewTrainTypeEast } from './train-type-east.model';

export const sampleWithRequiredData: ITrainTypeEast = {
  id: 92422,
};

export const sampleWithPartialData: ITrainTypeEast = {
  id: 79999,
  description: 'Savings optical',
};

export const sampleWithFullData: ITrainTypeEast = {
  id: 80822,
  code: 'Avon',
  description: 'Toys',
};

export const sampleWithNewData: NewTrainTypeEast = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
