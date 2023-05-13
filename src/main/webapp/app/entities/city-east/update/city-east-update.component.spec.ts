import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CityEastFormService } from './city-east-form.service';
import { CityEastService } from '../service/city-east.service';
import { ICityEast } from '../city-east.model';

import { CityEastUpdateComponent } from './city-east-update.component';

describe('CityEast Management Update Component', () => {
  let comp: CityEastUpdateComponent;
  let fixture: ComponentFixture<CityEastUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cityEastFormService: CityEastFormService;
  let cityEastService: CityEastService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CityEastUpdateComponent],
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
      .overrideTemplate(CityEastUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CityEastUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cityEastFormService = TestBed.inject(CityEastFormService);
    cityEastService = TestBed.inject(CityEastService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cityEast: ICityEast = { id: 456 };

      activatedRoute.data = of({ cityEast });
      comp.ngOnInit();

      expect(comp.cityEast).toEqual(cityEast);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICityEast>>();
      const cityEast = { id: 123 };
      jest.spyOn(cityEastFormService, 'getCityEast').mockReturnValue(cityEast);
      jest.spyOn(cityEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cityEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cityEast }));
      saveSubject.complete();

      // THEN
      expect(cityEastFormService.getCityEast).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cityEastService.update).toHaveBeenCalledWith(expect.objectContaining(cityEast));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICityEast>>();
      const cityEast = { id: 123 };
      jest.spyOn(cityEastFormService, 'getCityEast').mockReturnValue({ id: null });
      jest.spyOn(cityEastService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cityEast: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cityEast }));
      saveSubject.complete();

      // THEN
      expect(cityEastFormService.getCityEast).toHaveBeenCalled();
      expect(cityEastService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICityEast>>();
      const cityEast = { id: 123 };
      jest.spyOn(cityEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cityEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cityEastService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
