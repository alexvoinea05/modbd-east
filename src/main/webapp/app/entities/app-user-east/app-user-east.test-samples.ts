import { IAppUserEast, NewAppUserEast } from './app-user-east.model';

export const sampleWithRequiredData: IAppUserEast = {
  id: 56229,
};

export const sampleWithPartialData: IAppUserEast = {
  id: 66496,
  email: 'Elisa_Leannon@yahoo.com',
  firstName: 'Jannie',
  userTypeId: 72078,
};

export const sampleWithFullData: IAppUserEast = {
  id: 19691,
  email: 'Greg54@hotmail.com',
  balance: 89205,
  lastName: 'Mayert',
  firstName: 'Gaylord',
  userTypeId: 2075,
};

export const sampleWithNewData: NewAppUserEast = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
