export interface ICityEast {
  id: number;
  name?: string | null;
  districtId?: number | null;
}

export type NewCityEast = Omit<ICityEast, 'id'> & { id: null };
