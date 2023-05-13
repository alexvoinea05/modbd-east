import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../ticket-east.test-samples';

import { TicketEastFormService } from './ticket-east-form.service';

describe('TicketEast Form Service', () => {
  let service: TicketEastFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TicketEastFormService);
  });

  describe('Service methods', () => {
    describe('createTicketEastFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTicketEastFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            finalPrice: expect.any(Object),
            quantity: expect.any(Object),
            time: expect.any(Object),
            appUserId: expect.any(Object),
            journeyId: expect.any(Object),
          })
        );
      });

      it('passing ITicketEast should create a new form with FormGroup', () => {
        const formGroup = service.createTicketEastFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            finalPrice: expect.any(Object),
            quantity: expect.any(Object),
            time: expect.any(Object),
            appUserId: expect.any(Object),
            journeyId: expect.any(Object),
          })
        );
      });
    });

    describe('getTicketEast', () => {
      it('should return NewTicketEast for default TicketEast initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTicketEastFormGroup(sampleWithNewData);

        const ticketEast = service.getTicketEast(formGroup) as any;

        expect(ticketEast).toMatchObject(sampleWithNewData);
      });

      it('should return NewTicketEast for empty TicketEast initial value', () => {
        const formGroup = service.createTicketEastFormGroup();

        const ticketEast = service.getTicketEast(formGroup) as any;

        expect(ticketEast).toMatchObject({});
      });

      it('should return ITicketEast', () => {
        const formGroup = service.createTicketEastFormGroup(sampleWithRequiredData);

        const ticketEast = service.getTicketEast(formGroup) as any;

        expect(ticketEast).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITicketEast should not enable id FormControl', () => {
        const formGroup = service.createTicketEastFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTicketEast should disable id FormControl', () => {
        const formGroup = service.createTicketEastFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
