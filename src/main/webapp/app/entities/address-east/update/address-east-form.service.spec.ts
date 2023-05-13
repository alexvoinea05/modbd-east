import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../address-east.test-samples';

import { AddressEastFormService } from './address-east-form.service';

describe('AddressEast Form Service', () => {
  let service: AddressEastFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddressEastFormService);
  });

  describe('Service methods', () => {
    describe('createAddressEastFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAddressEastFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            streetNumber: expect.any(Object),
            street: expect.any(Object),
            zipcode: expect.any(Object),
            cityId: expect.any(Object),
          })
        );
      });

      it('passing IAddressEast should create a new form with FormGroup', () => {
        const formGroup = service.createAddressEastFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            streetNumber: expect.any(Object),
            street: expect.any(Object),
            zipcode: expect.any(Object),
            cityId: expect.any(Object),
          })
        );
      });
    });

    describe('getAddressEast', () => {
      it('should return NewAddressEast for default AddressEast initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAddressEastFormGroup(sampleWithNewData);

        const addressEast = service.getAddressEast(formGroup) as any;

        expect(addressEast).toMatchObject(sampleWithNewData);
      });

      it('should return NewAddressEast for empty AddressEast initial value', () => {
        const formGroup = service.createAddressEastFormGroup();

        const addressEast = service.getAddressEast(formGroup) as any;

        expect(addressEast).toMatchObject({});
      });

      it('should return IAddressEast', () => {
        const formGroup = service.createAddressEastFormGroup(sampleWithRequiredData);

        const addressEast = service.getAddressEast(formGroup) as any;

        expect(addressEast).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAddressEast should not enable id FormControl', () => {
        const formGroup = service.createAddressEastFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAddressEast should disable id FormControl', () => {
        const formGroup = service.createAddressEastFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
