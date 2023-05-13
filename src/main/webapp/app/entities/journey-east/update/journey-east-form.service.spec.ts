import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../journey-east.test-samples';

import { JourneyEastFormService } from './journey-east-form.service';

describe('JourneyEast Form Service', () => {
  let service: JourneyEastFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JourneyEastFormService);
  });

  describe('Service methods', () => {
    describe('createJourneyEastFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createJourneyEastFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            distance: expect.any(Object),
            journeyDuration: expect.any(Object),
            actualDepartureTime: expect.any(Object),
            plannedDepartureTime: expect.any(Object),
            actualArrivalTime: expect.any(Object),
            plannedArrivalTime: expect.any(Object),
            ticketPrice: expect.any(Object),
            numberOfStops: expect.any(Object),
            timeOfStops: expect.any(Object),
            minutesLate: expect.any(Object),
            journeyStatusId: expect.any(Object),
            trainId: expect.any(Object),
            companyId: expect.any(Object),
            departureRailwayStationId: expect.any(Object),
            arivalRailwayStationId: expect.any(Object),
          })
        );
      });

      it('passing IJourneyEast should create a new form with FormGroup', () => {
        const formGroup = service.createJourneyEastFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            distance: expect.any(Object),
            journeyDuration: expect.any(Object),
            actualDepartureTime: expect.any(Object),
            plannedDepartureTime: expect.any(Object),
            actualArrivalTime: expect.any(Object),
            plannedArrivalTime: expect.any(Object),
            ticketPrice: expect.any(Object),
            numberOfStops: expect.any(Object),
            timeOfStops: expect.any(Object),
            minutesLate: expect.any(Object),
            journeyStatusId: expect.any(Object),
            trainId: expect.any(Object),
            companyId: expect.any(Object),
            departureRailwayStationId: expect.any(Object),
            arivalRailwayStationId: expect.any(Object),
          })
        );
      });
    });

    describe('getJourneyEast', () => {
      it('should return NewJourneyEast for default JourneyEast initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createJourneyEastFormGroup(sampleWithNewData);

        const journeyEast = service.getJourneyEast(formGroup) as any;

        expect(journeyEast).toMatchObject(sampleWithNewData);
      });

      it('should return NewJourneyEast for empty JourneyEast initial value', () => {
        const formGroup = service.createJourneyEastFormGroup();

        const journeyEast = service.getJourneyEast(formGroup) as any;

        expect(journeyEast).toMatchObject({});
      });

      it('should return IJourneyEast', () => {
        const formGroup = service.createJourneyEastFormGroup(sampleWithRequiredData);

        const journeyEast = service.getJourneyEast(formGroup) as any;

        expect(journeyEast).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IJourneyEast should not enable id FormControl', () => {
        const formGroup = service.createJourneyEastFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewJourneyEast should disable id FormControl', () => {
        const formGroup = service.createJourneyEastFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
