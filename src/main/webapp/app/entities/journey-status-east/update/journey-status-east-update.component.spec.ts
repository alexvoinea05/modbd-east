import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { JourneyStatusEastFormService } from './journey-status-east-form.service';
import { JourneyStatusEastService } from '../service/journey-status-east.service';
import { IJourneyStatusEast } from '../journey-status-east.model';

import { JourneyStatusEastUpdateComponent } from './journey-status-east-update.component';

describe('JourneyStatusEast Management Update Component', () => {
  let comp: JourneyStatusEastUpdateComponent;
  let fixture: ComponentFixture<JourneyStatusEastUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let journeyStatusEastFormService: JourneyStatusEastFormService;
  let journeyStatusEastService: JourneyStatusEastService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [JourneyStatusEastUpdateComponent],
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
      .overrideTemplate(JourneyStatusEastUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JourneyStatusEastUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    journeyStatusEastFormService = TestBed.inject(JourneyStatusEastFormService);
    journeyStatusEastService = TestBed.inject(JourneyStatusEastService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const journeyStatusEast: IJourneyStatusEast = { id: 456 };

      activatedRoute.data = of({ journeyStatusEast });
      comp.ngOnInit();

      expect(comp.journeyStatusEast).toEqual(journeyStatusEast);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourneyStatusEast>>();
      const journeyStatusEast = { id: 123 };
      jest.spyOn(journeyStatusEastFormService, 'getJourneyStatusEast').mockReturnValue(journeyStatusEast);
      jest.spyOn(journeyStatusEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journeyStatusEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: journeyStatusEast }));
      saveSubject.complete();

      // THEN
      expect(journeyStatusEastFormService.getJourneyStatusEast).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(journeyStatusEastService.update).toHaveBeenCalledWith(expect.objectContaining(journeyStatusEast));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourneyStatusEast>>();
      const journeyStatusEast = { id: 123 };
      jest.spyOn(journeyStatusEastFormService, 'getJourneyStatusEast').mockReturnValue({ id: null });
      jest.spyOn(journeyStatusEastService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journeyStatusEast: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: journeyStatusEast }));
      saveSubject.complete();

      // THEN
      expect(journeyStatusEastFormService.getJourneyStatusEast).toHaveBeenCalled();
      expect(journeyStatusEastService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourneyStatusEast>>();
      const journeyStatusEast = { id: 123 };
      jest.spyOn(journeyStatusEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journeyStatusEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(journeyStatusEastService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
