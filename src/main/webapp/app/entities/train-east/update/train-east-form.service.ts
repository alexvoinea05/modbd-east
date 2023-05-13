import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITrainEast, NewTrainEast } from '../train-east.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITrainEast for edit and NewTrainEastFormGroupInput for create.
 */
type TrainEastFormGroupInput = ITrainEast | PartialWithRequiredKeyOf<NewTrainEast>;

type TrainEastFormDefaults = Pick<NewTrainEast, 'id'>;

type TrainEastFormGroupContent = {
  id: FormControl<ITrainEast['id'] | NewTrainEast['id']>;
  code: FormControl<ITrainEast['code']>;
  numberOfSeats: FormControl<ITrainEast['numberOfSeats']>;
  fuelTypeId: FormControl<ITrainEast['fuelTypeId']>;
  trainTypeId: FormControl<ITrainEast['trainTypeId']>;
};

export type TrainEastFormGroup = FormGroup<TrainEastFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TrainEastFormService {
  createTrainEastFormGroup(trainEast: TrainEastFormGroupInput = { id: null }): TrainEastFormGroup {
    const trainEastRawValue = {
      ...this.getFormDefaults(),
      ...trainEast,
    };
    return new FormGroup<TrainEastFormGroupContent>({
      id: new FormControl(
        { value: trainEastRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(trainEastRawValue.code),
      numberOfSeats: new FormControl(trainEastRawValue.numberOfSeats),
      fuelTypeId: new FormControl(trainEastRawValue.fuelTypeId),
      trainTypeId: new FormControl(trainEastRawValue.trainTypeId),
    });
  }

  getTrainEast(form: TrainEastFormGroup): ITrainEast | NewTrainEast {
    return form.getRawValue() as ITrainEast | NewTrainEast;
  }

  resetForm(form: TrainEastFormGroup, trainEast: TrainEastFormGroupInput): void {
    const trainEastRawValue = { ...this.getFormDefaults(), ...trainEast };
    form.reset(
      {
        ...trainEastRawValue,
        id: { value: trainEastRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TrainEastFormDefaults {
    return {
      id: null,
    };
  }
}
