import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../city-east.test-samples';

import { CityEastFormService } from './city-east-form.service';

describe('CityEast Form Service', () => {
  let service: CityEastFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CityEastFormService);
  });

  describe('Service methods', () => {
    describe('createCityEastFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCityEastFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            districtId: expect.any(Object),
          })
        );
      });

      it('passing ICityEast should create a new form with FormGroup', () => {
        const formGroup = service.createCityEastFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            districtId: expect.any(Object),
          })
        );
      });
    });

    describe('getCityEast', () => {
      it('should return NewCityEast for default CityEast initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCityEastFormGroup(sampleWithNewData);

        const cityEast = service.getCityEast(formGroup) as any;

        expect(cityEast).toMatchObject(sampleWithNewData);
      });

      it('should return NewCityEast for empty CityEast initial value', () => {
        const formGroup = service.createCityEastFormGroup();

        const cityEast = service.getCityEast(formGroup) as any;

        expect(cityEast).toMatchObject({});
      });

      it('should return ICityEast', () => {
        const formGroup = service.createCityEastFormGroup(sampleWithRequiredData);

        const cityEast = service.getCityEast(formGroup) as any;

        expect(cityEast).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICityEast should not enable id FormControl', () => {
        const formGroup = service.createCityEastFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCityEast should disable id FormControl', () => {
        const formGroup = service.createCityEastFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
