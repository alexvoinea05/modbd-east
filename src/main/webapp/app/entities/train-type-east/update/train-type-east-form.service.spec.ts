import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../train-type-east.test-samples';

import { TrainTypeEastFormService } from './train-type-east-form.service';

describe('TrainTypeEast Form Service', () => {
  let service: TrainTypeEastFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TrainTypeEastFormService);
  });

  describe('Service methods', () => {
    describe('createTrainTypeEastFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTrainTypeEastFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing ITrainTypeEast should create a new form with FormGroup', () => {
        const formGroup = service.createTrainTypeEastFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getTrainTypeEast', () => {
      it('should return NewTrainTypeEast for default TrainTypeEast initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTrainTypeEastFormGroup(sampleWithNewData);

        const trainTypeEast = service.getTrainTypeEast(formGroup) as any;

        expect(trainTypeEast).toMatchObject(sampleWithNewData);
      });

      it('should return NewTrainTypeEast for empty TrainTypeEast initial value', () => {
        const formGroup = service.createTrainTypeEastFormGroup();

        const trainTypeEast = service.getTrainTypeEast(formGroup) as any;

        expect(trainTypeEast).toMatchObject({});
      });

      it('should return ITrainTypeEast', () => {
        const formGroup = service.createTrainTypeEastFormGroup(sampleWithRequiredData);

        const trainTypeEast = service.getTrainTypeEast(formGroup) as any;

        expect(trainTypeEast).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITrainTypeEast should not enable id FormControl', () => {
        const formGroup = service.createTrainTypeEastFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTrainTypeEast should disable id FormControl', () => {
        const formGroup = service.createTrainTypeEastFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
