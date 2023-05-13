import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../app-user-east.test-samples';

import { AppUserEastFormService } from './app-user-east-form.service';

describe('AppUserEast Form Service', () => {
  let service: AppUserEastFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AppUserEastFormService);
  });

  describe('Service methods', () => {
    describe('createAppUserEastFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAppUserEastFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            email: expect.any(Object),
            balance: expect.any(Object),
            lastName: expect.any(Object),
            firstName: expect.any(Object),
            userTypeId: expect.any(Object),
          })
        );
      });

      it('passing IAppUserEast should create a new form with FormGroup', () => {
        const formGroup = service.createAppUserEastFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            email: expect.any(Object),
            balance: expect.any(Object),
            lastName: expect.any(Object),
            firstName: expect.any(Object),
            userTypeId: expect.any(Object),
          })
        );
      });
    });

    describe('getAppUserEast', () => {
      it('should return NewAppUserEast for default AppUserEast initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAppUserEastFormGroup(sampleWithNewData);

        const appUserEast = service.getAppUserEast(formGroup) as any;

        expect(appUserEast).toMatchObject(sampleWithNewData);
      });

      it('should return NewAppUserEast for empty AppUserEast initial value', () => {
        const formGroup = service.createAppUserEastFormGroup();

        const appUserEast = service.getAppUserEast(formGroup) as any;

        expect(appUserEast).toMatchObject({});
      });

      it('should return IAppUserEast', () => {
        const formGroup = service.createAppUserEastFormGroup(sampleWithRequiredData);

        const appUserEast = service.getAppUserEast(formGroup) as any;

        expect(appUserEast).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAppUserEast should not enable id FormControl', () => {
        const formGroup = service.createAppUserEastFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAppUserEast should disable id FormControl', () => {
        const formGroup = service.createAppUserEastFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
