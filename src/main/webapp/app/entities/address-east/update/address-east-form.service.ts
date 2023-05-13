import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAddressEast, NewAddressEast } from '../address-east.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAddressEast for edit and NewAddressEastFormGroupInput for create.
 */
type AddressEastFormGroupInput = IAddressEast | PartialWithRequiredKeyOf<NewAddressEast>;

type AddressEastFormDefaults = Pick<NewAddressEast, 'id'>;

type AddressEastFormGroupContent = {
  id: FormControl<IAddressEast['id'] | NewAddressEast['id']>;
  streetNumber: FormControl<IAddressEast['streetNumber']>;
  street: FormControl<IAddressEast['street']>;
  zipcode: FormControl<IAddressEast['zipcode']>;
  cityId: FormControl<IAddressEast['cityId']>;
};

export type AddressEastFormGroup = FormGroup<AddressEastFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AddressEastFormService {
  createAddressEastFormGroup(addressEast: AddressEastFormGroupInput = { id: null }): AddressEastFormGroup {
    const addressEastRawValue = {
      ...this.getFormDefaults(),
      ...addressEast,
    };
    return new FormGroup<AddressEastFormGroupContent>({
      id: new FormControl(
        { value: addressEastRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      streetNumber: new FormControl(addressEastRawValue.streetNumber),
      street: new FormControl(addressEastRawValue.street),
      zipcode: new FormControl(addressEastRawValue.zipcode),
      cityId: new FormControl(addressEastRawValue.cityId),
    });
  }

  getAddressEast(form: AddressEastFormGroup): IAddressEast | NewAddressEast {
    return form.getRawValue() as IAddressEast | NewAddressEast;
  }

  resetForm(form: AddressEastFormGroup, addressEast: AddressEastFormGroupInput): void {
    const addressEastRawValue = { ...this.getFormDefaults(), ...addressEast };
    form.reset(
      {
        ...addressEastRawValue,
        id: { value: addressEastRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AddressEastFormDefaults {
    return {
      id: null,
    };
  }
}
