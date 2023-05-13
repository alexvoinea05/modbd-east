export interface IAppUserEast {
  id: number;
  email?: string | null;
  balance?: number | null;
  lastName?: string | null;
  firstName?: string | null;
  userTypeId?: number | null;
}

export type NewAppUserEast = Omit<IAppUserEast, 'id'> & { id: null };
