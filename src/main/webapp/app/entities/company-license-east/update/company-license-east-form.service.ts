import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICompanyLicenseEast, NewCompanyLicenseEast } from '../company-license-east.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICompanyLicenseEast for edit and NewCompanyLicenseEastFormGroupInput for create.
 */
type CompanyLicenseEastFormGroupInput = ICompanyLicenseEast | PartialWithRequiredKeyOf<NewCompanyLicenseEast>;

type CompanyLicenseEastFormDefaults = Pick<NewCompanyLicenseEast, 'id'>;

type CompanyLicenseEastFormGroupContent = {
  id: FormControl<ICompanyLicenseEast['id'] | NewCompanyLicenseEast['id']>;
  idCompany: FormControl<ICompanyLicenseEast['idCompany']>;
  idLicense: FormControl<ICompanyLicenseEast['idLicense']>;
};

export type CompanyLicenseEastFormGroup = FormGroup<CompanyLicenseEastFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CompanyLicenseEastFormService {
  createCompanyLicenseEastFormGroup(companyLicenseEast: CompanyLicenseEastFormGroupInput = { id: null }): CompanyLicenseEastFormGroup {
    const companyLicenseEastRawValue = {
      ...this.getFormDefaults(),
      ...companyLicenseEast,
    };
    return new FormGroup<CompanyLicenseEastFormGroupContent>({
      id: new FormControl(
        { value: companyLicenseEastRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      idCompany: new FormControl(companyLicenseEastRawValue.idCompany),
      idLicense: new FormControl(companyLicenseEastRawValue.idLicense),
    });
  }

  getCompanyLicenseEast(form: CompanyLicenseEastFormGroup): ICompanyLicenseEast | NewCompanyLicenseEast {
    return form.getRawValue() as ICompanyLicenseEast | NewCompanyLicenseEast;
  }

  resetForm(form: CompanyLicenseEastFormGroup, companyLicenseEast: CompanyLicenseEastFormGroupInput): void {
    const companyLicenseEastRawValue = { ...this.getFormDefaults(), ...companyLicenseEast };
    form.reset(
      {
        ...companyLicenseEastRawValue,
        id: { value: companyLicenseEastRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CompanyLicenseEastFormDefaults {
    return {
      id: null,
    };
  }
}
