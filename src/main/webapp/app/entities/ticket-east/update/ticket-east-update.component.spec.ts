import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TicketEastFormService } from './ticket-east-form.service';
import { TicketEastService } from '../service/ticket-east.service';
import { ITicketEast } from '../ticket-east.model';

import { TicketEastUpdateComponent } from './ticket-east-update.component';

describe('TicketEast Management Update Component', () => {
  let comp: TicketEastUpdateComponent;
  let fixture: ComponentFixture<TicketEastUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ticketEastFormService: TicketEastFormService;
  let ticketEastService: TicketEastService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TicketEastUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TicketEastUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TicketEastUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ticketEastFormService = TestBed.inject(TicketEastFormService);
    ticketEastService = TestBed.inject(TicketEastService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const ticketEast: ITicketEast = { id: 456 };

      activatedRoute.data = of({ ticketEast });
      comp.ngOnInit();

      expect(comp.ticketEast).toEqual(ticketEast);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITicketEast>>();
      const ticketEast = { id: 123 };
      jest.spyOn(ticketEastFormService, 'getTicketEast').mockReturnValue(ticketEast);
      jest.spyOn(ticketEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ticketEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ticketEast }));
      saveSubject.complete();

      // THEN
      expect(ticketEastFormService.getTicketEast).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(ticketEastService.update).toHaveBeenCalledWith(expect.objectContaining(ticketEast));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITicketEast>>();
      const ticketEast = { id: 123 };
      jest.spyOn(ticketEastFormService, 'getTicketEast').mockReturnValue({ id: null });
      jest.spyOn(ticketEastService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ticketEast: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ticketEast }));
      saveSubject.complete();

      // THEN
      expect(ticketEastFormService.getTicketEast).toHaveBeenCalled();
      expect(ticketEastService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITicketEast>>();
      const ticketEast = { id: 123 };
      jest.spyOn(ticketEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ticketEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ticketEastService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
