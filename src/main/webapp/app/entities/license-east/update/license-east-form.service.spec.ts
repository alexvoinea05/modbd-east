import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../license-east.test-samples';

import { LicenseEastFormService } from './license-east-form.service';

describe('LicenseEast Form Service', () => {
  let service: LicenseEastFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LicenseEastFormService);
  });

  describe('Service methods', () => {
    describe('createLicenseEastFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLicenseEastFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            licenseNumber: expect.any(Object),
            licenseDescription: expect.any(Object),
          })
        );
      });

      it('passing ILicenseEast should create a new form with FormGroup', () => {
        const formGroup = service.createLicenseEastFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            licenseNumber: expect.any(Object),
            licenseDescription: expect.any(Object),
          })
        );
      });
    });

    describe('getLicenseEast', () => {
      it('should return NewLicenseEast for default LicenseEast initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLicenseEastFormGroup(sampleWithNewData);

        const licenseEast = service.getLicenseEast(formGroup) as any;

        expect(licenseEast).toMatchObject(sampleWithNewData);
      });

      it('should return NewLicenseEast for empty LicenseEast initial value', () => {
        const formGroup = service.createLicenseEastFormGroup();

        const licenseEast = service.getLicenseEast(formGroup) as any;

        expect(licenseEast).toMatchObject({});
      });

      it('should return ILicenseEast', () => {
        const formGroup = service.createLicenseEastFormGroup(sampleWithRequiredData);

        const licenseEast = service.getLicenseEast(formGroup) as any;

        expect(licenseEast).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILicenseEast should not enable id FormControl', () => {
        const formGroup = service.createLicenseEastFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLicenseEast should disable id FormControl', () => {
        const formGroup = service.createLicenseEastFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
