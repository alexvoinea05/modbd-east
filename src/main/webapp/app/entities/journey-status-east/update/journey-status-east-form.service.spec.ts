import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../journey-status-east.test-samples';

import { JourneyStatusEastFormService } from './journey-status-east-form.service';

describe('JourneyStatusEast Form Service', () => {
  let service: JourneyStatusEastFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JourneyStatusEastFormService);
  });

  describe('Service methods', () => {
    describe('createJourneyStatusEastFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createJourneyStatusEastFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing IJourneyStatusEast should create a new form with FormGroup', () => {
        const formGroup = service.createJourneyStatusEastFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getJourneyStatusEast', () => {
      it('should return NewJourneyStatusEast for default JourneyStatusEast initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createJourneyStatusEastFormGroup(sampleWithNewData);

        const journeyStatusEast = service.getJourneyStatusEast(formGroup) as any;

        expect(journeyStatusEast).toMatchObject(sampleWithNewData);
      });

      it('should return NewJourneyStatusEast for empty JourneyStatusEast initial value', () => {
        const formGroup = service.createJourneyStatusEastFormGroup();

        const journeyStatusEast = service.getJourneyStatusEast(formGroup) as any;

        expect(journeyStatusEast).toMatchObject({});
      });

      it('should return IJourneyStatusEast', () => {
        const formGroup = service.createJourneyStatusEastFormGroup(sampleWithRequiredData);

        const journeyStatusEast = service.getJourneyStatusEast(formGroup) as any;

        expect(journeyStatusEast).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IJourneyStatusEast should not enable id FormControl', () => {
        const formGroup = service.createJourneyStatusEastFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewJourneyStatusEast should disable id FormControl', () => {
        const formGroup = service.createJourneyStatusEastFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
