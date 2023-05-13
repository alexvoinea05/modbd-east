import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUserTypeEast, NewUserTypeEast } from '../user-type-east.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUserTypeEast for edit and NewUserTypeEastFormGroupInput for create.
 */
type UserTypeEastFormGroupInput = IUserTypeEast | PartialWithRequiredKeyOf<NewUserTypeEast>;

type UserTypeEastFormDefaults = Pick<NewUserTypeEast, 'id'>;

type UserTypeEastFormGroupContent = {
  id: FormControl<IUserTypeEast['id'] | NewUserTypeEast['id']>;
  code: FormControl<IUserTypeEast['code']>;
  discount: FormControl<IUserTypeEast['discount']>;
};

export type UserTypeEastFormGroup = FormGroup<UserTypeEastFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UserTypeEastFormService {
  createUserTypeEastFormGroup(userTypeEast: UserTypeEastFormGroupInput = { id: null }): UserTypeEastFormGroup {
    const userTypeEastRawValue = {
      ...this.getFormDefaults(),
      ...userTypeEast,
    };
    return new FormGroup<UserTypeEastFormGroupContent>({
      id: new FormControl(
        { value: userTypeEastRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(userTypeEastRawValue.code),
      discount: new FormControl(userTypeEastRawValue.discount),
    });
  }

  getUserTypeEast(form: UserTypeEastFormGroup): IUserTypeEast | NewUserTypeEast {
    return form.getRawValue() as IUserTypeEast | NewUserTypeEast;
  }

  resetForm(form: UserTypeEastFormGroup, userTypeEast: UserTypeEastFormGroupInput): void {
    const userTypeEastRawValue = { ...this.getFormDefaults(), ...userTypeEast };
    form.reset(
      {
        ...userTypeEastRawValue,
        id: { value: userTypeEastRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): UserTypeEastFormDefaults {
    return {
      id: null,
    };
  }
}
