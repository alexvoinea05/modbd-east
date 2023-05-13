export interface IFuelTypeEast {
  id: number;
  code?: string | null;
  description?: string | null;
}

export type NewFuelTypeEast = Omit<IFuelTypeEast, 'id'> & { id: null };
