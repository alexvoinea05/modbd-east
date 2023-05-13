import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITrainTypeEast, NewTrainTypeEast } from '../train-type-east.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITrainTypeEast for edit and NewTrainTypeEastFormGroupInput for create.
 */
type TrainTypeEastFormGroupInput = ITrainTypeEast | PartialWithRequiredKeyOf<NewTrainTypeEast>;

type TrainTypeEastFormDefaults = Pick<NewTrainTypeEast, 'id'>;

type TrainTypeEastFormGroupContent = {
  id: FormControl<ITrainTypeEast['id'] | NewTrainTypeEast['id']>;
  code: FormControl<ITrainTypeEast['code']>;
  description: FormControl<ITrainTypeEast['description']>;
};

export type TrainTypeEastFormGroup = FormGroup<TrainTypeEastFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TrainTypeEastFormService {
  createTrainTypeEastFormGroup(trainTypeEast: TrainTypeEastFormGroupInput = { id: null }): TrainTypeEastFormGroup {
    const trainTypeEastRawValue = {
      ...this.getFormDefaults(),
      ...trainTypeEast,
    };
    return new FormGroup<TrainTypeEastFormGroupContent>({
      id: new FormControl(
        { value: trainTypeEastRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(trainTypeEastRawValue.code),
      description: new FormControl(trainTypeEastRawValue.description),
    });
  }

  getTrainTypeEast(form: TrainTypeEastFormGroup): ITrainTypeEast | NewTrainTypeEast {
    return form.getRawValue() as ITrainTypeEast | NewTrainTypeEast;
  }

  resetForm(form: TrainTypeEastFormGroup, trainTypeEast: TrainTypeEastFormGroupInput): void {
    const trainTypeEastRawValue = { ...this.getFormDefaults(), ...trainTypeEast };
    form.reset(
      {
        ...trainTypeEastRawValue,
        id: { value: trainTypeEastRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TrainTypeEastFormDefaults {
    return {
      id: null,
    };
  }
}
