export interface ITrainEast {
  id: number;
  code?: string | null;
  numberOfSeats?: number | null;
  fuelTypeId?: number | null;
  trainTypeId?: number | null;
}

export type NewTrainEast = Omit<ITrainEast, 'id'> & { id: null };
