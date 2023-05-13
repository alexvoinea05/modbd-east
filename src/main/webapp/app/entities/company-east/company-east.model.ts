export interface ICompanyEast {
  id: number;
  name?: string | null;
  identificationNumber?: string | null;
}

export type NewCompanyEast = Omit<ICompanyEast, 'id'> & { id: null };
