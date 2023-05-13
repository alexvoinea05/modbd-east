import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FuelTypeEastFormService } from './fuel-type-east-form.service';
import { FuelTypeEastService } from '../service/fuel-type-east.service';
import { IFuelTypeEast } from '../fuel-type-east.model';

import { FuelTypeEastUpdateComponent } from './fuel-type-east-update.component';

describe('FuelTypeEast Management Update Component', () => {
  let comp: FuelTypeEastUpdateComponent;
  let fixture: ComponentFixture<FuelTypeEastUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fuelTypeEastFormService: FuelTypeEastFormService;
  let fuelTypeEastService: FuelTypeEastService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FuelTypeEastUpdateComponent],
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
      .overrideTemplate(FuelTypeEastUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FuelTypeEastUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fuelTypeEastFormService = TestBed.inject(FuelTypeEastFormService);
    fuelTypeEastService = TestBed.inject(FuelTypeEastService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const fuelTypeEast: IFuelTypeEast = { id: 456 };

      activatedRoute.data = of({ fuelTypeEast });
      comp.ngOnInit();

      expect(comp.fuelTypeEast).toEqual(fuelTypeEast);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelTypeEast>>();
      const fuelTypeEast = { id: 123 };
      jest.spyOn(fuelTypeEastFormService, 'getFuelTypeEast').mockReturnValue(fuelTypeEast);
      jest.spyOn(fuelTypeEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelTypeEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelTypeEast }));
      saveSubject.complete();

      // THEN
      expect(fuelTypeEastFormService.getFuelTypeEast).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fuelTypeEastService.update).toHaveBeenCalledWith(expect.objectContaining(fuelTypeEast));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelTypeEast>>();
      const fuelTypeEast = { id: 123 };
      jest.spyOn(fuelTypeEastFormService, 'getFuelTypeEast').mockReturnValue({ id: null });
      jest.spyOn(fuelTypeEastService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelTypeEast: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelTypeEast }));
      saveSubject.complete();

      // THEN
      expect(fuelTypeEastFormService.getFuelTypeEast).toHaveBeenCalled();
      expect(fuelTypeEastService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelTypeEast>>();
      const fuelTypeEast = { id: 123 };
      jest.spyOn(fuelTypeEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelTypeEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fuelTypeEastService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
