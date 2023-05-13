import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../company-east.test-samples';

import { CompanyEastFormService } from './company-east-form.service';

describe('CompanyEast Form Service', () => {
  let service: CompanyEastFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompanyEastFormService);
  });

  describe('Service methods', () => {
    describe('createCompanyEastFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCompanyEastFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            identificationNumber: expect.any(Object),
          })
        );
      });

      it('passing ICompanyEast should create a new form with FormGroup', () => {
        const formGroup = service.createCompanyEastFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            identificationNumber: expect.any(Object),
          })
        );
      });
    });

    describe('getCompanyEast', () => {
      it('should return NewCompanyEast for default CompanyEast initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCompanyEastFormGroup(sampleWithNewData);

        const companyEast = service.getCompanyEast(formGroup) as any;

        expect(companyEast).toMatchObject(sampleWithNewData);
      });

      it('should return NewCompanyEast for empty CompanyEast initial value', () => {
        const formGroup = service.createCompanyEastFormGroup();

        const companyEast = service.getCompanyEast(formGroup) as any;

        expect(companyEast).toMatchObject({});
      });

      it('should return ICompanyEast', () => {
        const formGroup = service.createCompanyEastFormGroup(sampleWithRequiredData);

        const companyEast = service.getCompanyEast(formGroup) as any;

        expect(companyEast).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICompanyEast should not enable id FormControl', () => {
        const formGroup = service.createCompanyEastFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCompanyEast should disable id FormControl', () => {
        const formGroup = service.createCompanyEastFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
