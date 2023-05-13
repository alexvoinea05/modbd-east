import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IJourneyStatusEast, NewJourneyStatusEast } from '../journey-status-east.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IJourneyStatusEast for edit and NewJourneyStatusEastFormGroupInput for create.
 */
type JourneyStatusEastFormGroupInput = IJourneyStatusEast | PartialWithRequiredKeyOf<NewJourneyStatusEast>;

type JourneyStatusEastFormDefaults = Pick<NewJourneyStatusEast, 'id'>;

type JourneyStatusEastFormGroupContent = {
  id: FormControl<IJourneyStatusEast['id'] | NewJourneyStatusEast['id']>;
  code: FormControl<IJourneyStatusEast['code']>;
  description: FormControl<IJourneyStatusEast['description']>;
};

export type JourneyStatusEastFormGroup = FormGroup<JourneyStatusEastFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class JourneyStatusEastFormService {
  createJourneyStatusEastFormGroup(journeyStatusEast: JourneyStatusEastFormGroupInput = { id: null }): JourneyStatusEastFormGroup {
    const journeyStatusEastRawValue = {
      ...this.getFormDefaults(),
      ...journeyStatusEast,
    };
    return new FormGroup<JourneyStatusEastFormGroupContent>({
      id: new FormControl(
        { value: journeyStatusEastRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(journeyStatusEastRawValue.code),
      description: new FormControl(journeyStatusEastRawValue.description),
    });
  }

  getJourneyStatusEast(form: JourneyStatusEastFormGroup): IJourneyStatusEast | NewJourneyStatusEast {
    return form.getRawValue() as IJourneyStatusEast | NewJourneyStatusEast;
  }

  resetForm(form: JourneyStatusEastFormGroup, journeyStatusEast: JourneyStatusEastFormGroupInput): void {
    const journeyStatusEastRawValue = { ...this.getFormDefaults(), ...journeyStatusEast };
    form.reset(
      {
        ...journeyStatusEastRawValue,
        id: { value: journeyStatusEastRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): JourneyStatusEastFormDefaults {
    return {
      id: null,
    };
  }
}
