import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../railway-station-east.test-samples';

import { RailwayStationEastFormService } from './railway-station-east-form.service';

describe('RailwayStationEast Form Service', () => {
  let service: RailwayStationEastFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RailwayStationEastFormService);
  });

  describe('Service methods', () => {
    describe('createRailwayStationEastFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRailwayStationEastFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            railwayStationName: expect.any(Object),
            railwayTypeId: expect.any(Object),
            addressId: expect.any(Object),
          })
        );
      });

      it('passing IRailwayStationEast should create a new form with FormGroup', () => {
        const formGroup = service.createRailwayStationEastFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            railwayStationName: expect.any(Object),
            railwayTypeId: expect.any(Object),
            addressId: expect.any(Object),
          })
        );
      });
    });

    describe('getRailwayStationEast', () => {
      it('should return NewRailwayStationEast for default RailwayStationEast initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRailwayStationEastFormGroup(sampleWithNewData);

        const railwayStationEast = service.getRailwayStationEast(formGroup) as any;

        expect(railwayStationEast).toMatchObject(sampleWithNewData);
      });

      it('should return NewRailwayStationEast for empty RailwayStationEast initial value', () => {
        const formGroup = service.createRailwayStationEastFormGroup();

        const railwayStationEast = service.getRailwayStationEast(formGroup) as any;

        expect(railwayStationEast).toMatchObject({});
      });

      it('should return IRailwayStationEast', () => {
        const formGroup = service.createRailwayStationEastFormGroup(sampleWithRequiredData);

        const railwayStationEast = service.getRailwayStationEast(formGroup) as any;

        expect(railwayStationEast).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRailwayStationEast should not enable id FormControl', () => {
        const formGroup = service.createRailwayStationEastFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRailwayStationEast should disable id FormControl', () => {
        const formGroup = service.createRailwayStationEastFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
