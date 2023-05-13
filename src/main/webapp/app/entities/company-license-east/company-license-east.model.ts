import { ICompanyEast } from 'app/entities/company-east/company-east.model';
import { ILicenseEast } from 'app/entities/license-east/license-east.model';

export interface ICompanyLicenseEast {
  id: number;
  idCompany?: Pick<ICompanyEast, 'id'> | null;
  idLicense?: Pick<ILicenseEast, 'id'> | null;
}

export type NewCompanyLicenseEast = Omit<ICompanyLicenseEast, 'id'> & { id: null };
