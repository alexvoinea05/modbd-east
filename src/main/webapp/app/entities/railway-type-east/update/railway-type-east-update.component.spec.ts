import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RailwayTypeEastFormService } from './railway-type-east-form.service';
import { RailwayTypeEastService } from '../service/railway-type-east.service';
import { IRailwayTypeEast } from '../railway-type-east.model';

import { RailwayTypeEastUpdateComponent } from './railway-type-east-update.component';

describe('RailwayTypeEast Management Update Component', () => {
  let comp: RailwayTypeEastUpdateComponent;
  let fixture: ComponentFixture<RailwayTypeEastUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let railwayTypeEastFormService: RailwayTypeEastFormService;
  let railwayTypeEastService: RailwayTypeEastService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RailwayTypeEastUpdateComponent],
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
      .overrideTemplate(RailwayTypeEastUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RailwayTypeEastUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    railwayTypeEastFormService = TestBed.inject(RailwayTypeEastFormService);
    railwayTypeEastService = TestBed.inject(RailwayTypeEastService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const railwayTypeEast: IRailwayTypeEast = { id: 456 };

      activatedRoute.data = of({ railwayTypeEast });
      comp.ngOnInit();

      expect(comp.railwayTypeEast).toEqual(railwayTypeEast);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayTypeEast>>();
      const railwayTypeEast = { id: 123 };
      jest.spyOn(railwayTypeEastFormService, 'getRailwayTypeEast').mockReturnValue(railwayTypeEast);
      jest.spyOn(railwayTypeEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayTypeEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: railwayTypeEast }));
      saveSubject.complete();

      // THEN
      expect(railwayTypeEastFormService.getRailwayTypeEast).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(railwayTypeEastService.update).toHaveBeenCalledWith(expect.objectContaining(railwayTypeEast));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayTypeEast>>();
      const railwayTypeEast = { id: 123 };
      jest.spyOn(railwayTypeEastFormService, 'getRailwayTypeEast').mockReturnValue({ id: null });
      jest.spyOn(railwayTypeEastService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayTypeEast: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: railwayTypeEast }));
      saveSubject.complete();

      // THEN
      expect(railwayTypeEastFormService.getRailwayTypeEast).toHaveBeenCalled();
      expect(railwayTypeEastService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayTypeEast>>();
      const railwayTypeEast = { id: 123 };
      jest.spyOn(railwayTypeEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayTypeEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(railwayTypeEastService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
