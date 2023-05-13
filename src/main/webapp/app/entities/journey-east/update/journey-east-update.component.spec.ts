import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { JourneyEastFormService } from './journey-east-form.service';
import { JourneyEastService } from '../service/journey-east.service';
import { IJourneyEast } from '../journey-east.model';

import { JourneyEastUpdateComponent } from './journey-east-update.component';

describe('JourneyEast Management Update Component', () => {
  let comp: JourneyEastUpdateComponent;
  let fixture: ComponentFixture<JourneyEastUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let journeyEastFormService: JourneyEastFormService;
  let journeyEastService: JourneyEastService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [JourneyEastUpdateComponent],
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
      .overrideTemplate(JourneyEastUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JourneyEastUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    journeyEastFormService = TestBed.inject(JourneyEastFormService);
    journeyEastService = TestBed.inject(JourneyEastService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const journeyEast: IJourneyEast = { id: 456 };

      activatedRoute.data = of({ journeyEast });
      comp.ngOnInit();

      expect(comp.journeyEast).toEqual(journeyEast);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourneyEast>>();
      const journeyEast = { id: 123 };
      jest.spyOn(journeyEastFormService, 'getJourneyEast').mockReturnValue(journeyEast);
      jest.spyOn(journeyEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journeyEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: journeyEast }));
      saveSubject.complete();

      // THEN
      expect(journeyEastFormService.getJourneyEast).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(journeyEastService.update).toHaveBeenCalledWith(expect.objectContaining(journeyEast));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourneyEast>>();
      const journeyEast = { id: 123 };
      jest.spyOn(journeyEastFormService, 'getJourneyEast').mockReturnValue({ id: null });
      jest.spyOn(journeyEastService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journeyEast: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: journeyEast }));
      saveSubject.complete();

      // THEN
      expect(journeyEastFormService.getJourneyEast).toHaveBeenCalled();
      expect(journeyEastService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourneyEast>>();
      const journeyEast = { id: 123 };
      jest.spyOn(journeyEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journeyEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(journeyEastService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
