import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LicenseEastFormService } from './license-east-form.service';
import { LicenseEastService } from '../service/license-east.service';
import { ILicenseEast } from '../license-east.model';

import { LicenseEastUpdateComponent } from './license-east-update.component';

describe('LicenseEast Management Update Component', () => {
  let comp: LicenseEastUpdateComponent;
  let fixture: ComponentFixture<LicenseEastUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let licenseEastFormService: LicenseEastFormService;
  let licenseEastService: LicenseEastService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LicenseEastUpdateComponent],
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
      .overrideTemplate(LicenseEastUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LicenseEastUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    licenseEastFormService = TestBed.inject(LicenseEastFormService);
    licenseEastService = TestBed.inject(LicenseEastService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const licenseEast: ILicenseEast = { id: 456 };

      activatedRoute.data = of({ licenseEast });
      comp.ngOnInit();

      expect(comp.licenseEast).toEqual(licenseEast);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILicenseEast>>();
      const licenseEast = { id: 123 };
      jest.spyOn(licenseEastFormService, 'getLicenseEast').mockReturnValue(licenseEast);
      jest.spyOn(licenseEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ licenseEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: licenseEast }));
      saveSubject.complete();

      // THEN
      expect(licenseEastFormService.getLicenseEast).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(licenseEastService.update).toHaveBeenCalledWith(expect.objectContaining(licenseEast));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILicenseEast>>();
      const licenseEast = { id: 123 };
      jest.spyOn(licenseEastFormService, 'getLicenseEast').mockReturnValue({ id: null });
      jest.spyOn(licenseEastService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ licenseEast: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: licenseEast }));
      saveSubject.complete();

      // THEN
      expect(licenseEastFormService.getLicenseEast).toHaveBeenCalled();
      expect(licenseEastService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILicenseEast>>();
      const licenseEast = { id: 123 };
      jest.spyOn(licenseEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ licenseEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(licenseEastService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
