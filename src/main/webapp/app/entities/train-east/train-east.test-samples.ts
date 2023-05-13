import { ITrainEast, NewTrainEast } from './train-east.model';

export const sampleWithRequiredData: ITrainEast = {
  id: 95102,
};

export const sampleWithPartialData: ITrainEast = {
  id: 39034,
  code: 'visualize Sausages experiences',
  numberOfSeats: 73133,
  fuelTypeId: 83650,
  trainTypeId: 11833,
};

export const sampleWithFullData: ITrainEast = {
  id: 20451,
  code: 'black',
  numberOfSeats: 60896,
  fuelTypeId: 30849,
  trainTypeId: 67196,
};

export const sampleWithNewData: NewTrainEast = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
