import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICityEast, NewCityEast } from '../city-east.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICityEast for edit and NewCityEastFormGroupInput for create.
 */
type CityEastFormGroupInput = ICityEast | PartialWithRequiredKeyOf<NewCityEast>;

type CityEastFormDefaults = Pick<NewCityEast, 'id'>;

type CityEastFormGroupContent = {
  id: FormControl<ICityEast['id'] | NewCityEast['id']>;
  name: FormControl<ICityEast['name']>;
  districtId: FormControl<ICityEast['districtId']>;
};

export type CityEastFormGroup = FormGroup<CityEastFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CityEastFormService {
  createCityEastFormGroup(cityEast: CityEastFormGroupInput = { id: null }): CityEastFormGroup {
    const cityEastRawValue = {
      ...this.getFormDefaults(),
      ...cityEast,
    };
    return new FormGroup<CityEastFormGroupContent>({
      id: new FormControl(
        { value: cityEastRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(cityEastRawValue.name),
      districtId: new FormControl(cityEastRawValue.districtId),
    });
  }

  getCityEast(form: CityEastFormGroup): ICityEast | NewCityEast {
    return form.getRawValue() as ICityEast | NewCityEast;
  }

  resetForm(form: CityEastFormGroup, cityEast: CityEastFormGroupInput): void {
    const cityEastRawValue = { ...this.getFormDefaults(), ...cityEast };
    form.reset(
      {
        ...cityEastRawValue,
        id: { value: cityEastRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CityEastFormDefaults {
    return {
      id: null,
    };
  }
}
