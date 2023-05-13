export interface IRailwayStationEast {
  id: number;
  railwayStationName?: string | null;
  railwayTypeId?: number | null;
  addressId?: number | null;
}

export type NewRailwayStationEast = Omit<IRailwayStationEast, 'id'> & { id: null };
