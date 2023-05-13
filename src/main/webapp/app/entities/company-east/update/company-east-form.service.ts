import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICompanyEast, NewCompanyEast } from '../company-east.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICompanyEast for edit and NewCompanyEastFormGroupInput for create.
 */
type CompanyEastFormGroupInput = ICompanyEast | PartialWithRequiredKeyOf<NewCompanyEast>;

type CompanyEastFormDefaults = Pick<NewCompanyEast, 'id'>;

type CompanyEastFormGroupContent = {
  id: FormControl<ICompanyEast['id'] | NewCompanyEast['id']>;
  name: FormControl<ICompanyEast['name']>;
  identificationNumber: FormControl<ICompanyEast['identificationNumber']>;
};

export type CompanyEastFormGroup = FormGroup<CompanyEastFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CompanyEastFormService {
  createCompanyEastFormGroup(companyEast: CompanyEastFormGroupInput = { id: null }): CompanyEastFormGroup {
    const companyEastRawValue = {
      ...this.getFormDefaults(),
      ...companyEast,
    };
    return new FormGroup<CompanyEastFormGroupContent>({
      id: new FormControl(
        { value: companyEastRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(companyEastRawValue.name),
      identificationNumber: new FormControl(companyEastRawValue.identificationNumber),
    });
  }

  getCompanyEast(form: CompanyEastFormGroup): ICompanyEast | NewCompanyEast {
    return form.getRawValue() as ICompanyEast | NewCompanyEast;
  }

  resetForm(form: CompanyEastFormGroup, companyEast: CompanyEastFormGroupInput): void {
    const companyEastRawValue = { ...this.getFormDefaults(), ...companyEast };
    form.reset(
      {
        ...companyEastRawValue,
        id: { value: companyEastRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CompanyEastFormDefaults {
    return {
      id: null,
    };
  }
}
