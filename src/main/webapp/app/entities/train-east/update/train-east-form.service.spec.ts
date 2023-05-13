import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../train-east.test-samples';

import { TrainEastFormService } from './train-east-form.service';

describe('TrainEast Form Service', () => {
  let service: TrainEastFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TrainEastFormService);
  });

  describe('Service methods', () => {
    describe('createTrainEastFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTrainEastFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            numberOfSeats: expect.any(Object),
            fuelTypeId: expect.any(Object),
            trainTypeId: expect.any(Object),
          })
        );
      });

      it('passing ITrainEast should create a new form with FormGroup', () => {
        const formGroup = service.createTrainEastFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            numberOfSeats: expect.any(Object),
            fuelTypeId: expect.any(Object),
            trainTypeId: expect.any(Object),
          })
        );
      });
    });

    describe('getTrainEast', () => {
      it('should return NewTrainEast for default TrainEast initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTrainEastFormGroup(sampleWithNewData);

        const trainEast = service.getTrainEast(formGroup) as any;

        expect(trainEast).toMatchObject(sampleWithNewData);
      });

      it('should return NewTrainEast for empty TrainEast initial value', () => {
        const formGroup = service.createTrainEastFormGroup();

        const trainEast = service.getTrainEast(formGroup) as any;

        expect(trainEast).toMatchObject({});
      });

      it('should return ITrainEast', () => {
        const formGroup = service.createTrainEastFormGroup(sampleWithRequiredData);

        const trainEast = service.getTrainEast(formGroup) as any;

        expect(trainEast).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITrainEast should not enable id FormControl', () => {
        const formGroup = service.createTrainEastFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTrainEast should disable id FormControl', () => {
        const formGroup = service.createTrainEastFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
