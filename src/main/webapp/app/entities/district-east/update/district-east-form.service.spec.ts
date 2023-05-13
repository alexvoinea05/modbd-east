import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../district-east.test-samples';

import { DistrictEastFormService } from './district-east-form.service';

describe('DistrictEast Form Service', () => {
  let service: DistrictEastFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DistrictEastFormService);
  });

  describe('Service methods', () => {
    describe('createDistrictEastFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDistrictEastFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            region: expect.any(Object),
          })
        );
      });

      it('passing IDistrictEast should create a new form with FormGroup', () => {
        const formGroup = service.createDistrictEastFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            region: expect.any(Object),
          })
        );
      });
    });

    describe('getDistrictEast', () => {
      it('should return NewDistrictEast for default DistrictEast initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDistrictEastFormGroup(sampleWithNewData);

        const districtEast = service.getDistrictEast(formGroup) as any;

        expect(districtEast).toMatchObject(sampleWithNewData);
      });

      it('should return NewDistrictEast for empty DistrictEast initial value', () => {
        const formGroup = service.createDistrictEastFormGroup();

        const districtEast = service.getDistrictEast(formGroup) as any;

        expect(districtEast).toMatchObject({});
      });

      it('should return IDistrictEast', () => {
        const formGroup = service.createDistrictEastFormGroup(sampleWithRequiredData);

        const districtEast = service.getDistrictEast(formGroup) as any;

        expect(districtEast).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDistrictEast should not enable id FormControl', () => {
        const formGroup = service.createDistrictEastFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDistrictEast should disable id FormControl', () => {
        const formGroup = service.createDistrictEastFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
