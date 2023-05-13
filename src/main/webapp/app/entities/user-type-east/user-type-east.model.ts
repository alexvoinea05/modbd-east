export interface IUserTypeEast {
  id: number;
  code?: string | null;
  discount?: number | null;
}

export type NewUserTypeEast = Omit<IUserTypeEast, 'id'> & { id: null };
