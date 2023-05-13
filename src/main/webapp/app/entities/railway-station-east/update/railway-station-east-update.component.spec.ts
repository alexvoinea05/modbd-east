import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RailwayStationEastFormService } from './railway-station-east-form.service';
import { RailwayStationEastService } from '../service/railway-station-east.service';
import { IRailwayStationEast } from '../railway-station-east.model';

import { RailwayStationEastUpdateComponent } from './railway-station-east-update.component';

describe('RailwayStationEast Management Update Component', () => {
  let comp: RailwayStationEastUpdateComponent;
  let fixture: ComponentFixture<RailwayStationEastUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let railwayStationEastFormService: RailwayStationEastFormService;
  let railwayStationEastService: RailwayStationEastService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RailwayStationEastUpdateComponent],
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
      .overrideTemplate(RailwayStationEastUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RailwayStationEastUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    railwayStationEastFormService = TestBed.inject(RailwayStationEastFormService);
    railwayStationEastService = TestBed.inject(RailwayStationEastService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const railwayStationEast: IRailwayStationEast = { id: 456 };

      activatedRoute.data = of({ railwayStationEast });
      comp.ngOnInit();

      expect(comp.railwayStationEast).toEqual(railwayStationEast);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayStationEast>>();
      const railwayStationEast = { id: 123 };
      jest.spyOn(railwayStationEastFormService, 'getRailwayStationEast').mockReturnValue(railwayStationEast);
      jest.spyOn(railwayStationEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayStationEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: railwayStationEast }));
      saveSubject.complete();

      // THEN
      expect(railwayStationEastFormService.getRailwayStationEast).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(railwayStationEastService.update).toHaveBeenCalledWith(expect.objectContaining(railwayStationEast));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayStationEast>>();
      const railwayStationEast = { id: 123 };
      jest.spyOn(railwayStationEastFormService, 'getRailwayStationEast').mockReturnValue({ id: null });
      jest.spyOn(railwayStationEastService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayStationEast: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: railwayStationEast }));
      saveSubject.complete();

      // THEN
      expect(railwayStationEastFormService.getRailwayStationEast).toHaveBeenCalled();
      expect(railwayStationEastService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayStationEast>>();
      const railwayStationEast = { id: 123 };
      jest.spyOn(railwayStationEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayStationEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(railwayStationEastService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
