export interface IDistrictEast {
  id: number;
  name?: string | null;
  region?: string | null;
}

export type NewDistrictEast = Omit<IDistrictEast, 'id'> & { id: null };
