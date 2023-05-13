import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CompanyEastFormService } from './company-east-form.service';
import { CompanyEastService } from '../service/company-east.service';
import { ICompanyEast } from '../company-east.model';

import { CompanyEastUpdateComponent } from './company-east-update.component';

describe('CompanyEast Management Update Component', () => {
  let comp: CompanyEastUpdateComponent;
  let fixture: ComponentFixture<CompanyEastUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let companyEastFormService: CompanyEastFormService;
  let companyEastService: CompanyEastService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CompanyEastUpdateComponent],
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
      .overrideTemplate(CompanyEastUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CompanyEastUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    companyEastFormService = TestBed.inject(CompanyEastFormService);
    companyEastService = TestBed.inject(CompanyEastService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const companyEast: ICompanyEast = { id: 456 };

      activatedRoute.data = of({ companyEast });
      comp.ngOnInit();

      expect(comp.companyEast).toEqual(companyEast);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanyEast>>();
      const companyEast = { id: 123 };
      jest.spyOn(companyEastFormService, 'getCompanyEast').mockReturnValue(companyEast);
      jest.spyOn(companyEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: companyEast }));
      saveSubject.complete();

      // THEN
      expect(companyEastFormService.getCompanyEast).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(companyEastService.update).toHaveBeenCalledWith(expect.objectContaining(companyEast));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanyEast>>();
      const companyEast = { id: 123 };
      jest.spyOn(companyEastFormService, 'getCompanyEast').mockReturnValue({ id: null });
      jest.spyOn(companyEastService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyEast: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: companyEast }));
      saveSubject.complete();

      // THEN
      expect(companyEastFormService.getCompanyEast).toHaveBeenCalled();
      expect(companyEastService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanyEast>>();
      const companyEast = { id: 123 };
      jest.spyOn(companyEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(companyEastService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
