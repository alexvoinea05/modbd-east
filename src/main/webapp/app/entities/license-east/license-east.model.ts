export interface ILicenseEast {
  id: number;
  licenseNumber?: number | null;
  licenseDescription?: string | null;
}

export type NewLicenseEast = Omit<ILicenseEast, 'id'> & { id: null };
