import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILicenseEast, NewLicenseEast } from '../license-east.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILicenseEast for edit and NewLicenseEastFormGroupInput for create.
 */
type LicenseEastFormGroupInput = ILicenseEast | PartialWithRequiredKeyOf<NewLicenseEast>;

type LicenseEastFormDefaults = Pick<NewLicenseEast, 'id'>;

type LicenseEastFormGroupContent = {
  id: FormControl<ILicenseEast['id'] | NewLicenseEast['id']>;
  licenseNumber: FormControl<ILicenseEast['licenseNumber']>;
  licenseDescription: FormControl<ILicenseEast['licenseDescription']>;
};

export type LicenseEastFormGroup = FormGroup<LicenseEastFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LicenseEastFormService {
  createLicenseEastFormGroup(licenseEast: LicenseEastFormGroupInput = { id: null }): LicenseEastFormGroup {
    const licenseEastRawValue = {
      ...this.getFormDefaults(),
      ...licenseEast,
    };
    return new FormGroup<LicenseEastFormGroupContent>({
      id: new FormControl(
        { value: licenseEastRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      licenseNumber: new FormControl(licenseEastRawValue.licenseNumber),
      licenseDescription: new FormControl(licenseEastRawValue.licenseDescription),
    });
  }

  getLicenseEast(form: LicenseEastFormGroup): ILicenseEast | NewLicenseEast {
    return form.getRawValue() as ILicenseEast | NewLicenseEast;
  }

  resetForm(form: LicenseEastFormGroup, licenseEast: LicenseEastFormGroupInput): void {
    const licenseEastRawValue = { ...this.getFormDefaults(), ...licenseEast };
    form.reset(
      {
        ...licenseEastRawValue,
        id: { value: licenseEastRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LicenseEastFormDefaults {
    return {
      id: null,
    };
  }
}
