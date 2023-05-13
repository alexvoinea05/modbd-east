import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDistrictEast, NewDistrictEast } from '../district-east.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDistrictEast for edit and NewDistrictEastFormGroupInput for create.
 */
type DistrictEastFormGroupInput = IDistrictEast | PartialWithRequiredKeyOf<NewDistrictEast>;

type DistrictEastFormDefaults = Pick<NewDistrictEast, 'id'>;

type DistrictEastFormGroupContent = {
  id: FormControl<IDistrictEast['id'] | NewDistrictEast['id']>;
  name: FormControl<IDistrictEast['name']>;
  region: FormControl<IDistrictEast['region']>;
};

export type DistrictEastFormGroup = FormGroup<DistrictEastFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DistrictEastFormService {
  createDistrictEastFormGroup(districtEast: DistrictEastFormGroupInput = { id: null }): DistrictEastFormGroup {
    const districtEastRawValue = {
      ...this.getFormDefaults(),
      ...districtEast,
    };
    return new FormGroup<DistrictEastFormGroupContent>({
      id: new FormControl(
        { value: districtEastRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(districtEastRawValue.name),
      region: new FormControl(districtEastRawValue.region),
    });
  }

  getDistrictEast(form: DistrictEastFormGroup): IDistrictEast | NewDistrictEast {
    return form.getRawValue() as IDistrictEast | NewDistrictEast;
  }

  resetForm(form: DistrictEastFormGroup, districtEast: DistrictEastFormGroupInput): void {
    const districtEastRawValue = { ...this.getFormDefaults(), ...districtEast };
    form.reset(
      {
        ...districtEastRawValue,
        id: { value: districtEastRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DistrictEastFormDefaults {
    return {
      id: null,
    };
  }
}
