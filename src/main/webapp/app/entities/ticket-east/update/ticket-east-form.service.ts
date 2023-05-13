import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITicketEast, NewTicketEast } from '../ticket-east.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITicketEast for edit and NewTicketEastFormGroupInput for create.
 */
type TicketEastFormGroupInput = ITicketEast | PartialWithRequiredKeyOf<NewTicketEast>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITicketEast | NewTicketEast> = Omit<T, 'time'> & {
  time?: string | null;
};

type TicketEastFormRawValue = FormValueOf<ITicketEast>;

type NewTicketEastFormRawValue = FormValueOf<NewTicketEast>;

type TicketEastFormDefaults = Pick<NewTicketEast, 'id' | 'time'>;

type TicketEastFormGroupContent = {
  id: FormControl<TicketEastFormRawValue['id'] | NewTicketEast['id']>;
  finalPrice: FormControl<TicketEastFormRawValue['finalPrice']>;
  quantity: FormControl<TicketEastFormRawValue['quantity']>;
  time: FormControl<TicketEastFormRawValue['time']>;
  appUserId: FormControl<TicketEastFormRawValue['appUserId']>;
  journeyId: FormControl<TicketEastFormRawValue['journeyId']>;
};

export type TicketEastFormGroup = FormGroup<TicketEastFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TicketEastFormService {
  createTicketEastFormGroup(ticketEast: TicketEastFormGroupInput = { id: null }): TicketEastFormGroup {
    const ticketEastRawValue = this.convertTicketEastToTicketEastRawValue({
      ...this.getFormDefaults(),
      ...ticketEast,
    });
    return new FormGroup<TicketEastFormGroupContent>({
      id: new FormControl(
        { value: ticketEastRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      finalPrice: new FormControl(ticketEastRawValue.finalPrice),
      quantity: new FormControl(ticketEastRawValue.quantity),
      time: new FormControl(ticketEastRawValue.time),
      appUserId: new FormControl(ticketEastRawValue.appUserId),
      journeyId: new FormControl(ticketEastRawValue.journeyId),
    });
  }

  getTicketEast(form: TicketEastFormGroup): ITicketEast | NewTicketEast {
    return this.convertTicketEastRawValueToTicketEast(form.getRawValue() as TicketEastFormRawValue | NewTicketEastFormRawValue);
  }

  resetForm(form: TicketEastFormGroup, ticketEast: TicketEastFormGroupInput): void {
    const ticketEastRawValue = this.convertTicketEastToTicketEastRawValue({ ...this.getFormDefaults(), ...ticketEast });
    form.reset(
      {
        ...ticketEastRawValue,
        id: { value: ticketEastRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TicketEastFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      time: currentTime,
    };
  }

  private convertTicketEastRawValueToTicketEast(
    rawTicketEast: TicketEastFormRawValue | NewTicketEastFormRawValue
  ): ITicketEast | NewTicketEast {
    return {
      ...rawTicketEast,
      time: dayjs(rawTicketEast.time, DATE_TIME_FORMAT),
    };
  }

  private convertTicketEastToTicketEastRawValue(
    ticketEast: ITicketEast | (Partial<NewTicketEast> & TicketEastFormDefaults)
  ): TicketEastFormRawValue | PartialWithRequiredKeyOf<NewTicketEastFormRawValue> {
    return {
      ...ticketEast,
      time: ticketEast.time ? ticketEast.time.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
