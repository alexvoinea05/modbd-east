import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRailwayStationEast, NewRailwayStationEast } from '../railway-station-east.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRailwayStationEast for edit and NewRailwayStationEastFormGroupInput for create.
 */
type RailwayStationEastFormGroupInput = IRailwayStationEast | PartialWithRequiredKeyOf<NewRailwayStationEast>;

type RailwayStationEastFormDefaults = Pick<NewRailwayStationEast, 'id'>;

type RailwayStationEastFormGroupContent = {
  id: FormControl<IRailwayStationEast['id'] | NewRailwayStationEast['id']>;
  railwayStationName: FormControl<IRailwayStationEast['railwayStationName']>;
  railwayTypeId: FormControl<IRailwayStationEast['railwayTypeId']>;
  addressId: FormControl<IRailwayStationEast['addressId']>;
};

export type RailwayStationEastFormGroup = FormGroup<RailwayStationEastFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RailwayStationEastFormService {
  createRailwayStationEastFormGroup(railwayStationEast: RailwayStationEastFormGroupInput = { id: null }): RailwayStationEastFormGroup {
    const railwayStationEastRawValue = {
      ...this.getFormDefaults(),
      ...railwayStationEast,
    };
    return new FormGroup<RailwayStationEastFormGroupContent>({
      id: new FormControl(
        { value: railwayStationEastRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      railwayStationName: new FormControl(railwayStationEastRawValue.railwayStationName),
      railwayTypeId: new FormControl(railwayStationEastRawValue.railwayTypeId),
      addressId: new FormControl(railwayStationEastRawValue.addressId),
    });
  }

  getRailwayStationEast(form: RailwayStationEastFormGroup): IRailwayStationEast | NewRailwayStationEast {
    return form.getRawValue() as IRailwayStationEast | NewRailwayStationEast;
  }

  resetForm(form: RailwayStationEastFormGroup, railwayStationEast: RailwayStationEastFormGroupInput): void {
    const railwayStationEastRawValue = { ...this.getFormDefaults(), ...railwayStationEast };
    form.reset(
      {
        ...railwayStationEastRawValue,
        id: { value: railwayStationEastRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RailwayStationEastFormDefaults {
    return {
      id: null,
    };
  }
}
