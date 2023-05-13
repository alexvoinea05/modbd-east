import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DistrictEastFormService } from './district-east-form.service';
import { DistrictEastService } from '../service/district-east.service';
import { IDistrictEast } from '../district-east.model';

import { DistrictEastUpdateComponent } from './district-east-update.component';

describe('DistrictEast Management Update Component', () => {
  let comp: DistrictEastUpdateComponent;
  let fixture: ComponentFixture<DistrictEastUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let districtEastFormService: DistrictEastFormService;
  let districtEastService: DistrictEastService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DistrictEastUpdateComponent],
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
      .overrideTemplate(DistrictEastUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DistrictEastUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    districtEastFormService = TestBed.inject(DistrictEastFormService);
    districtEastService = TestBed.inject(DistrictEastService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const districtEast: IDistrictEast = { id: 456 };

      activatedRoute.data = of({ districtEast });
      comp.ngOnInit();

      expect(comp.districtEast).toEqual(districtEast);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDistrictEast>>();
      const districtEast = { id: 123 };
      jest.spyOn(districtEastFormService, 'getDistrictEast').mockReturnValue(districtEast);
      jest.spyOn(districtEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ districtEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: districtEast }));
      saveSubject.complete();

      // THEN
      expect(districtEastFormService.getDistrictEast).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(districtEastService.update).toHaveBeenCalledWith(expect.objectContaining(districtEast));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDistrictEast>>();
      const districtEast = { id: 123 };
      jest.spyOn(districtEastFormService, 'getDistrictEast').mockReturnValue({ id: null });
      jest.spyOn(districtEastService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ districtEast: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: districtEast }));
      saveSubject.complete();

      // THEN
      expect(districtEastFormService.getDistrictEast).toHaveBeenCalled();
      expect(districtEastService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDistrictEast>>();
      const districtEast = { id: 123 };
      jest.spyOn(districtEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ districtEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(districtEastService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
