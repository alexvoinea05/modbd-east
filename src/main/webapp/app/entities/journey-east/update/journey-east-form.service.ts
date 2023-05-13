import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IJourneyEast, NewJourneyEast } from '../journey-east.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IJourneyEast for edit and NewJourneyEastFormGroupInput for create.
 */
type JourneyEastFormGroupInput = IJourneyEast | PartialWithRequiredKeyOf<NewJourneyEast>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IJourneyEast | NewJourneyEast> = Omit<
  T,
  'actualDepartureTime' | 'plannedDepartureTime' | 'actualArrivalTime' | 'plannedArrivalTime'
> & {
  actualDepartureTime?: string | null;
  plannedDepartureTime?: string | null;
  actualArrivalTime?: string | null;
  plannedArrivalTime?: string | null;
};

type JourneyEastFormRawValue = FormValueOf<IJourneyEast>;

type NewJourneyEastFormRawValue = FormValueOf<NewJourneyEast>;

type JourneyEastFormDefaults = Pick<
  NewJourneyEast,
  'id' | 'actualDepartureTime' | 'plannedDepartureTime' | 'actualArrivalTime' | 'plannedArrivalTime'
>;

type JourneyEastFormGroupContent = {
  id: FormControl<JourneyEastFormRawValue['id'] | NewJourneyEast['id']>;
  distance: FormControl<JourneyEastFormRawValue['distance']>;
  journeyDuration: FormControl<JourneyEastFormRawValue['journeyDuration']>;
  actualDepartureTime: FormControl<JourneyEastFormRawValue['actualDepartureTime']>;
  plannedDepartureTime: FormControl<JourneyEastFormRawValue['plannedDepartureTime']>;
  actualArrivalTime: FormControl<JourneyEastFormRawValue['actualArrivalTime']>;
  plannedArrivalTime: FormControl<JourneyEastFormRawValue['plannedArrivalTime']>;
  ticketPrice: FormControl<JourneyEastFormRawValue['ticketPrice']>;
  numberOfStops: FormControl<JourneyEastFormRawValue['numberOfStops']>;
  timeOfStops: FormControl<JourneyEastFormRawValue['timeOfStops']>;
  minutesLate: FormControl<JourneyEastFormRawValue['minutesLate']>;
  journeyStatusId: FormControl<JourneyEastFormRawValue['journeyStatusId']>;
  trainId: FormControl<JourneyEastFormRawValue['trainId']>;
  companyId: FormControl<JourneyEastFormRawValue['companyId']>;
  departureRailwayStationId: FormControl<JourneyEastFormRawValue['departureRailwayStationId']>;
  arivalRailwayStationId: FormControl<JourneyEastFormRawValue['arivalRailwayStationId']>;
};

export type JourneyEastFormGroup = FormGroup<JourneyEastFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class JourneyEastFormService {
  createJourneyEastFormGroup(journeyEast: JourneyEastFormGroupInput = { id: null }): JourneyEastFormGroup {
    const journeyEastRawValue = this.convertJourneyEastToJourneyEastRawValue({
      ...this.getFormDefaults(),
      ...journeyEast,
    });
    return new FormGroup<JourneyEastFormGroupContent>({
      id: new FormControl(
        { value: journeyEastRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      distance: new FormControl(journeyEastRawValue.distance),
      journeyDuration: new FormControl(journeyEastRawValue.journeyDuration),
      actualDepartureTime: new FormControl(journeyEastRawValue.actualDepartureTime),
      plannedDepartureTime: new FormControl(journeyEastRawValue.plannedDepartureTime),
      actualArrivalTime: new FormControl(journeyEastRawValue.actualArrivalTime),
      plannedArrivalTime: new FormControl(journeyEastRawValue.plannedArrivalTime),
      ticketPrice: new FormControl(journeyEastRawValue.ticketPrice),
      numberOfStops: new FormControl(journeyEastRawValue.numberOfStops),
      timeOfStops: new FormControl(journeyEastRawValue.timeOfStops),
      minutesLate: new FormControl(journeyEastRawValue.minutesLate),
      journeyStatusId: new FormControl(journeyEastRawValue.journeyStatusId),
      trainId: new FormControl(journeyEastRawValue.trainId),
      companyId: new FormControl(journeyEastRawValue.companyId),
      departureRailwayStationId: new FormControl(journeyEastRawValue.departureRailwayStationId),
      arivalRailwayStationId: new FormControl(journeyEastRawValue.arivalRailwayStationId),
    });
  }

  getJourneyEast(form: JourneyEastFormGroup): IJourneyEast | NewJourneyEast {
    return this.convertJourneyEastRawValueToJourneyEast(form.getRawValue() as JourneyEastFormRawValue | NewJourneyEastFormRawValue);
  }

  resetForm(form: JourneyEastFormGroup, journeyEast: JourneyEastFormGroupInput): void {
    const journeyEastRawValue = this.convertJourneyEastToJourneyEastRawValue({ ...this.getFormDefaults(), ...journeyEast });
    form.reset(
      {
        ...journeyEastRawValue,
        id: { value: journeyEastRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): JourneyEastFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      actualDepartureTime: currentTime,
      plannedDepartureTime: currentTime,
      actualArrivalTime: currentTime,
      plannedArrivalTime: currentTime,
    };
  }

  private convertJourneyEastRawValueToJourneyEast(
    rawJourneyEast: JourneyEastFormRawValue | NewJourneyEastFormRawValue
  ): IJourneyEast | NewJourneyEast {
    return {
      ...rawJourneyEast,
      actualDepartureTime: dayjs(rawJourneyEast.actualDepartureTime, DATE_TIME_FORMAT),
      plannedDepartureTime: dayjs(rawJourneyEast.plannedDepartureTime, DATE_TIME_FORMAT),
      actualArrivalTime: dayjs(rawJourneyEast.actualArrivalTime, DATE_TIME_FORMAT),
      plannedArrivalTime: dayjs(rawJourneyEast.plannedArrivalTime, DATE_TIME_FORMAT),
    };
  }

  private convertJourneyEastToJourneyEastRawValue(
    journeyEast: IJourneyEast | (Partial<NewJourneyEast> & JourneyEastFormDefaults)
  ): JourneyEastFormRawValue | PartialWithRequiredKeyOf<NewJourneyEastFormRawValue> {
    return {
      ...journeyEast,
      actualDepartureTime: journeyEast.actualDepartureTime ? journeyEast.actualDepartureTime.format(DATE_TIME_FORMAT) : undefined,
      plannedDepartureTime: journeyEast.plannedDepartureTime ? journeyEast.plannedDepartureTime.format(DATE_TIME_FORMAT) : undefined,
      actualArrivalTime: journeyEast.actualArrivalTime ? journeyEast.actualArrivalTime.format(DATE_TIME_FORMAT) : undefined,
      plannedArrivalTime: journeyEast.plannedArrivalTime ? journeyEast.plannedArrivalTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
