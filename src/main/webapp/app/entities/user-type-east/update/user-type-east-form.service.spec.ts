import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../user-type-east.test-samples';

import { UserTypeEastFormService } from './user-type-east-form.service';

describe('UserTypeEast Form Service', () => {
  let service: UserTypeEastFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserTypeEastFormService);
  });

  describe('Service methods', () => {
    describe('createUserTypeEastFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUserTypeEastFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            discount: expect.any(Object),
          })
        );
      });

      it('passing IUserTypeEast should create a new form with FormGroup', () => {
        const formGroup = service.createUserTypeEastFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            discount: expect.any(Object),
          })
        );
      });
    });

    describe('getUserTypeEast', () => {
      it('should return NewUserTypeEast for default UserTypeEast initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createUserTypeEastFormGroup(sampleWithNewData);

        const userTypeEast = service.getUserTypeEast(formGroup) as any;

        expect(userTypeEast).toMatchObject(sampleWithNewData);
      });

      it('should return NewUserTypeEast for empty UserTypeEast initial value', () => {
        const formGroup = service.createUserTypeEastFormGroup();

        const userTypeEast = service.getUserTypeEast(formGroup) as any;

        expect(userTypeEast).toMatchObject({});
      });

      it('should return IUserTypeEast', () => {
        const formGroup = service.createUserTypeEastFormGroup(sampleWithRequiredData);

        const userTypeEast = service.getUserTypeEast(formGroup) as any;

        expect(userTypeEast).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUserTypeEast should not enable id FormControl', () => {
        const formGroup = service.createUserTypeEastFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUserTypeEast should disable id FormControl', () => {
        const formGroup = service.createUserTypeEastFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
