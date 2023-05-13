import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFuelTypeEast, NewFuelTypeEast } from '../fuel-type-east.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFuelTypeEast for edit and NewFuelTypeEastFormGroupInput for create.
 */
type FuelTypeEastFormGroupInput = IFuelTypeEast | PartialWithRequiredKeyOf<NewFuelTypeEast>;

type FuelTypeEastFormDefaults = Pick<NewFuelTypeEast, 'id'>;

type FuelTypeEastFormGroupContent = {
  id: FormControl<IFuelTypeEast['id'] | NewFuelTypeEast['id']>;
  code: FormControl<IFuelTypeEast['code']>;
  description: FormControl<IFuelTypeEast['description']>;
};

export type FuelTypeEastFormGroup = FormGroup<FuelTypeEastFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FuelTypeEastFormService {
  createFuelTypeEastFormGroup(fuelTypeEast: FuelTypeEastFormGroupInput = { id: null }): FuelTypeEastFormGroup {
    const fuelTypeEastRawValue = {
      ...this.getFormDefaults(),
      ...fuelTypeEast,
    };
    return new FormGroup<FuelTypeEastFormGroupContent>({
      id: new FormControl(
        { value: fuelTypeEastRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(fuelTypeEastRawValue.code),
      description: new FormControl(fuelTypeEastRawValue.description),
    });
  }

  getFuelTypeEast(form: FuelTypeEastFormGroup): IFuelTypeEast | NewFuelTypeEast {
    return form.getRawValue() as IFuelTypeEast | NewFuelTypeEast;
  }

  resetForm(form: FuelTypeEastFormGroup, fuelTypeEast: FuelTypeEastFormGroupInput): void {
    const fuelTypeEastRawValue = { ...this.getFormDefaults(), ...fuelTypeEast };
    form.reset(
      {
        ...fuelTypeEastRawValue,
        id: { value: fuelTypeEastRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FuelTypeEastFormDefaults {
    return {
      id: null,
    };
  }
}
