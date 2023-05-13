export interface ITrainTypeEast {
  id: number;
  code?: string | null;
  description?: string | null;
}

export type NewTrainTypeEast = Omit<ITrainTypeEast, 'id'> & { id: null };
