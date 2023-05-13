import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../company-license-east.test-samples';

import { CompanyLicenseEastFormService } from './company-license-east-form.service';

describe('CompanyLicenseEast Form Service', () => {
  let service: CompanyLicenseEastFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompanyLicenseEastFormService);
  });

  describe('Service methods', () => {
    describe('createCompanyLicenseEastFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCompanyLicenseEastFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idCompany: expect.any(Object),
            idLicense: expect.any(Object),
          })
        );
      });

      it('passing ICompanyLicenseEast should create a new form with FormGroup', () => {
        const formGroup = service.createCompanyLicenseEastFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idCompany: expect.any(Object),
            idLicense: expect.any(Object),
          })
        );
      });
    });

    describe('getCompanyLicenseEast', () => {
      it('should return NewCompanyLicenseEast for default CompanyLicenseEast initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCompanyLicenseEastFormGroup(sampleWithNewData);

        const companyLicenseEast = service.getCompanyLicenseEast(formGroup) as any;

        expect(companyLicenseEast).toMatchObject(sampleWithNewData);
      });

      it('should return NewCompanyLicenseEast for empty CompanyLicenseEast initial value', () => {
        const formGroup = service.createCompanyLicenseEastFormGroup();

        const companyLicenseEast = service.getCompanyLicenseEast(formGroup) as any;

        expect(companyLicenseEast).toMatchObject({});
      });

      it('should return ICompanyLicenseEast', () => {
        const formGroup = service.createCompanyLicenseEastFormGroup(sampleWithRequiredData);

        const companyLicenseEast = service.getCompanyLicenseEast(formGroup) as any;

        expect(companyLicenseEast).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICompanyLicenseEast should not enable id FormControl', () => {
        const formGroup = service.createCompanyLicenseEastFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCompanyLicenseEast should disable id FormControl', () => {
        const formGroup = service.createCompanyLicenseEastFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
