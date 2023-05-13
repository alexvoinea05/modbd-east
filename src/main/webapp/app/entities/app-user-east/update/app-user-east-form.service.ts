import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAppUserEast, NewAppUserEast } from '../app-user-east.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAppUserEast for edit and NewAppUserEastFormGroupInput for create.
 */
type AppUserEastFormGroupInput = IAppUserEast | PartialWithRequiredKeyOf<NewAppUserEast>;

type AppUserEastFormDefaults = Pick<NewAppUserEast, 'id'>;

type AppUserEastFormGroupContent = {
  id: FormControl<IAppUserEast['id'] | NewAppUserEast['id']>;
  email: FormControl<IAppUserEast['email']>;
  balance: FormControl<IAppUserEast['balance']>;
  lastName: FormControl<IAppUserEast['lastName']>;
  firstName: FormControl<IAppUserEast['firstName']>;
  userTypeId: FormControl<IAppUserEast['userTypeId']>;
};

export type AppUserEastFormGroup = FormGroup<AppUserEastFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AppUserEastFormService {
  createAppUserEastFormGroup(appUserEast: AppUserEastFormGroupInput = { id: null }): AppUserEastFormGroup {
    const appUserEastRawValue = {
      ...this.getFormDefaults(),
      ...appUserEast,
    };
    return new FormGroup<AppUserEastFormGroupContent>({
      id: new FormControl(
        { value: appUserEastRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      email: new FormControl(appUserEastRawValue.email),
      balance: new FormControl(appUserEastRawValue.balance),
      lastName: new FormControl(appUserEastRawValue.lastName),
      firstName: new FormControl(appUserEastRawValue.firstName),
      userTypeId: new FormControl(appUserEastRawValue.userTypeId),
    });
  }

  getAppUserEast(form: AppUserEastFormGroup): IAppUserEast | NewAppUserEast {
    return form.getRawValue() as IAppUserEast | NewAppUserEast;
  }

  resetForm(form: AppUserEastFormGroup, appUserEast: AppUserEastFormGroupInput): void {
    const appUserEastRawValue = { ...this.getFormDefaults(), ...appUserEast };
    form.reset(
      {
        ...appUserEastRawValue,
        id: { value: appUserEastRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AppUserEastFormDefaults {
    return {
      id: null,
    };
  }
}
