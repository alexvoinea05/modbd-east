import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../railway-type-east.test-samples';

import { RailwayTypeEastFormService } from './railway-type-east-form.service';

describe('RailwayTypeEast Form Service', () => {
  let service: RailwayTypeEastFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RailwayTypeEastFormService);
  });

  describe('Service methods', () => {
    describe('createRailwayTypeEastFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRailwayTypeEastFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing IRailwayTypeEast should create a new form with FormGroup', () => {
        const formGroup = service.createRailwayTypeEastFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getRailwayTypeEast', () => {
      it('should return NewRailwayTypeEast for default RailwayTypeEast initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRailwayTypeEastFormGroup(sampleWithNewData);

        const railwayTypeEast = service.getRailwayTypeEast(formGroup) as any;

        expect(railwayTypeEast).toMatchObject(sampleWithNewData);
      });

      it('should return NewRailwayTypeEast for empty RailwayTypeEast initial value', () => {
        const formGroup = service.createRailwayTypeEastFormGroup();

        const railwayTypeEast = service.getRailwayTypeEast(formGroup) as any;

        expect(railwayTypeEast).toMatchObject({});
      });

      it('should return IRailwayTypeEast', () => {
        const formGroup = service.createRailwayTypeEastFormGroup(sampleWithRequiredData);

        const railwayTypeEast = service.getRailwayTypeEast(formGroup) as any;

        expect(railwayTypeEast).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRailwayTypeEast should not enable id FormControl', () => {
        const formGroup = service.createRailwayTypeEastFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRailwayTypeEast should disable id FormControl', () => {
        const formGroup = service.createRailwayTypeEastFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
