import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CompanyLicenseEastFormService } from './company-license-east-form.service';
import { CompanyLicenseEastService } from '../service/company-license-east.service';
import { ICompanyLicenseEast } from '../company-license-east.model';
import { ICompanyEast } from 'app/entities/company-east/company-east.model';
import { CompanyEastService } from 'app/entities/company-east/service/company-east.service';
import { ILicenseEast } from 'app/entities/license-east/license-east.model';
import { LicenseEastService } from 'app/entities/license-east/service/license-east.service';

import { CompanyLicenseEastUpdateComponent } from './company-license-east-update.component';

describe('CompanyLicenseEast Management Update Component', () => {
  let comp: CompanyLicenseEastUpdateComponent;
  let fixture: ComponentFixture<CompanyLicenseEastUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let companyLicenseEastFormService: CompanyLicenseEastFormService;
  let companyLicenseEastService: CompanyLicenseEastService;
  let companyEastService: CompanyEastService;
  let licenseEastService: LicenseEastService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CompanyLicenseEastUpdateComponent],
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
      .overrideTemplate(CompanyLicenseEastUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CompanyLicenseEastUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    companyLicenseEastFormService = TestBed.inject(CompanyLicenseEastFormService);
    companyLicenseEastService = TestBed.inject(CompanyLicenseEastService);
    companyEastService = TestBed.inject(CompanyEastService);
    licenseEastService = TestBed.inject(LicenseEastService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CompanyEast query and add missing value', () => {
      const companyLicenseEast: ICompanyLicenseEast = { id: 456 };
      const idCompany: ICompanyEast = { id: 79156 };
      companyLicenseEast.idCompany = idCompany;

      const companyEastCollection: ICompanyEast[] = [{ id: 39173 }];
      jest.spyOn(companyEastService, 'query').mockReturnValue(of(new HttpResponse({ body: companyEastCollection })));
      const additionalCompanyEasts = [idCompany];
      const expectedCollection: ICompanyEast[] = [...additionalCompanyEasts, ...companyEastCollection];
      jest.spyOn(companyEastService, 'addCompanyEastToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ companyLicenseEast });
      comp.ngOnInit();

      expect(companyEastService.query).toHaveBeenCalled();
      expect(companyEastService.addCompanyEastToCollectionIfMissing).toHaveBeenCalledWith(
        companyEastCollection,
        ...additionalCompanyEasts.map(expect.objectContaining)
      );
      expect(comp.companyEastsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LicenseEast query and add missing value', () => {
      const companyLicenseEast: ICompanyLicenseEast = { id: 456 };
      const idLicense: ILicenseEast = { id: 42884 };
      companyLicenseEast.idLicense = idLicense;

      const licenseEastCollection: ILicenseEast[] = [{ id: 56996 }];
      jest.spyOn(licenseEastService, 'query').mockReturnValue(of(new HttpResponse({ body: licenseEastCollection })));
      const additionalLicenseEasts = [idLicense];
      const expectedCollection: ILicenseEast[] = [...additionalLicenseEasts, ...licenseEastCollection];
      jest.spyOn(licenseEastService, 'addLicenseEastToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ companyLicenseEast });
      comp.ngOnInit();

      expect(licenseEastService.query).toHaveBeenCalled();
      expect(licenseEastService.addLicenseEastToCollectionIfMissing).toHaveBeenCalledWith(
        licenseEastCollection,
        ...additionalLicenseEasts.map(expect.objectContaining)
      );
      expect(comp.licenseEastsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const companyLicenseEast: ICompanyLicenseEast = { id: 456 };
      const idCompany: ICompanyEast = { id: 98550 };
      companyLicenseEast.idCompany = idCompany;
      const idLicense: ILicenseEast = { id: 46403 };
      companyLicenseEast.idLicense = idLicense;

      activatedRoute.data = of({ companyLicenseEast });
      comp.ngOnInit();

      expect(comp.companyEastsSharedCollection).toContain(idCompany);
      expect(comp.licenseEastsSharedCollection).toContain(idLicense);
      expect(comp.companyLicenseEast).toEqual(companyLicenseEast);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanyLicenseEast>>();
      const companyLicenseEast = { id: 123 };
      jest.spyOn(companyLicenseEastFormService, 'getCompanyLicenseEast').mockReturnValue(companyLicenseEast);
      jest.spyOn(companyLicenseEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyLicenseEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: companyLicenseEast }));
      saveSubject.complete();

      // THEN
      expect(companyLicenseEastFormService.getCompanyLicenseEast).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(companyLicenseEastService.update).toHaveBeenCalledWith(expect.objectContaining(companyLicenseEast));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanyLicenseEast>>();
      const companyLicenseEast = { id: 123 };
      jest.spyOn(companyLicenseEastFormService, 'getCompanyLicenseEast').mockReturnValue({ id: null });
      jest.spyOn(companyLicenseEastService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyLicenseEast: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: companyLicenseEast }));
      saveSubject.complete();

      // THEN
      expect(companyLicenseEastFormService.getCompanyLicenseEast).toHaveBeenCalled();
      expect(companyLicenseEastService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanyLicenseEast>>();
      const companyLicenseEast = { id: 123 };
      jest.spyOn(companyLicenseEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyLicenseEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(companyLicenseEastService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCompanyEast', () => {
      it('Should forward to companyEastService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(companyEastService, 'compareCompanyEast');
        comp.compareCompanyEast(entity, entity2);
        expect(companyEastService.compareCompanyEast).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareLicenseEast', () => {
      it('Should forward to licenseEastService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(licenseEastService, 'compareLicenseEast');
        comp.compareLicenseEast(entity, entity2);
        expect(licenseEastService.compareLicenseEast).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
