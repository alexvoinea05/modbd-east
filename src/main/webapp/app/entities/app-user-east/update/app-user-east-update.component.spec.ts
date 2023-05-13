import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AppUserEastFormService } from './app-user-east-form.service';
import { AppUserEastService } from '../service/app-user-east.service';
import { IAppUserEast } from '../app-user-east.model';

import { AppUserEastUpdateComponent } from './app-user-east-update.component';

describe('AppUserEast Management Update Component', () => {
  let comp: AppUserEastUpdateComponent;
  let fixture: ComponentFixture<AppUserEastUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let appUserEastFormService: AppUserEastFormService;
  let appUserEastService: AppUserEastService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AppUserEastUpdateComponent],
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
      .overrideTemplate(AppUserEastUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AppUserEastUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    appUserEastFormService = TestBed.inject(AppUserEastFormService);
    appUserEastService = TestBed.inject(AppUserEastService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const appUserEast: IAppUserEast = { id: 456 };

      activatedRoute.data = of({ appUserEast });
      comp.ngOnInit();

      expect(comp.appUserEast).toEqual(appUserEast);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAppUserEast>>();
      const appUserEast = { id: 123 };
      jest.spyOn(appUserEastFormService, 'getAppUserEast').mockReturnValue(appUserEast);
      jest.spyOn(appUserEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appUserEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: appUserEast }));
      saveSubject.complete();

      // THEN
      expect(appUserEastFormService.getAppUserEast).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(appUserEastService.update).toHaveBeenCalledWith(expect.objectContaining(appUserEast));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAppUserEast>>();
      const appUserEast = { id: 123 };
      jest.spyOn(appUserEastFormService, 'getAppUserEast').mockReturnValue({ id: null });
      jest.spyOn(appUserEastService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appUserEast: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: appUserEast }));
      saveSubject.complete();

      // THEN
      expect(appUserEastFormService.getAppUserEast).toHaveBeenCalled();
      expect(appUserEastService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAppUserEast>>();
      const appUserEast = { id: 123 };
      jest.spyOn(appUserEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appUserEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(appUserEastService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
