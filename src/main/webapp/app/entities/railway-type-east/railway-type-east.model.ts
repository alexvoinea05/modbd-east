export interface IRailwayTypeEast {
  id: number;
  code?: string | null;
  description?: string | null;
}

export type NewRailwayTypeEast = Omit<IRailwayTypeEast, 'id'> & { id: null };
