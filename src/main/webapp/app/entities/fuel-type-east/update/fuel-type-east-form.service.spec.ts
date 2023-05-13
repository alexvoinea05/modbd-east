import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fuel-type-east.test-samples';

import { FuelTypeEastFormService } from './fuel-type-east-form.service';

describe('FuelTypeEast Form Service', () => {
  let service: FuelTypeEastFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FuelTypeEastFormService);
  });

  describe('Service methods', () => {
    describe('createFuelTypeEastFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFuelTypeEastFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing IFuelTypeEast should create a new form with FormGroup', () => {
        const formGroup = service.createFuelTypeEastFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getFuelTypeEast', () => {
      it('should return NewFuelTypeEast for default FuelTypeEast initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFuelTypeEastFormGroup(sampleWithNewData);

        const fuelTypeEast = service.getFuelTypeEast(formGroup) as any;

        expect(fuelTypeEast).toMatchObject(sampleWithNewData);
      });

      it('should return NewFuelTypeEast for empty FuelTypeEast initial value', () => {
        const formGroup = service.createFuelTypeEastFormGroup();

        const fuelTypeEast = service.getFuelTypeEast(formGroup) as any;

        expect(fuelTypeEast).toMatchObject({});
      });

      it('should return IFuelTypeEast', () => {
        const formGroup = service.createFuelTypeEastFormGroup(sampleWithRequiredData);

        const fuelTypeEast = service.getFuelTypeEast(formGroup) as any;

        expect(fuelTypeEast).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFuelTypeEast should not enable id FormControl', () => {
        const formGroup = service.createFuelTypeEastFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFuelTypeEast should disable id FormControl', () => {
        const formGroup = service.createFuelTypeEastFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
