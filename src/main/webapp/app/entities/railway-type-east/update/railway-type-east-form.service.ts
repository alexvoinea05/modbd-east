import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRailwayTypeEast, NewRailwayTypeEast } from '../railway-type-east.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRailwayTypeEast for edit and NewRailwayTypeEastFormGroupInput for create.
 */
type RailwayTypeEastFormGroupInput = IRailwayTypeEast | PartialWithRequiredKeyOf<NewRailwayTypeEast>;

type RailwayTypeEastFormDefaults = Pick<NewRailwayTypeEast, 'id'>;

type RailwayTypeEastFormGroupContent = {
  id: FormControl<IRailwayTypeEast['id'] | NewRailwayTypeEast['id']>;
  code: FormControl<IRailwayTypeEast['code']>;
  description: FormControl<IRailwayTypeEast['description']>;
};

export type RailwayTypeEastFormGroup = FormGroup<RailwayTypeEastFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RailwayTypeEastFormService {
  createRailwayTypeEastFormGroup(railwayTypeEast: RailwayTypeEastFormGroupInput = { id: null }): RailwayTypeEastFormGroup {
    const railwayTypeEastRawValue = {
      ...this.getFormDefaults(),
      ...railwayTypeEast,
    };
    return new FormGroup<RailwayTypeEastFormGroupContent>({
      id: new FormControl(
        { value: railwayTypeEastRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(railwayTypeEastRawValue.code),
      description: new FormControl(railwayTypeEastRawValue.description),
    });
  }

  getRailwayTypeEast(form: RailwayTypeEastFormGroup): IRailwayTypeEast | NewRailwayTypeEast {
    return form.getRawValue() as IRailwayTypeEast | NewRailwayTypeEast;
  }

  resetForm(form: RailwayTypeEastFormGroup, railwayTypeEast: RailwayTypeEastFormGroupInput): void {
    const railwayTypeEastRawValue = { ...this.getFormDefaults(), ...railwayTypeEast };
    form.reset(
      {
        ...railwayTypeEastRawValue,
        id: { value: railwayTypeEastRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RailwayTypeEastFormDefaults {
    return {
      id: null,
    };
  }
}
