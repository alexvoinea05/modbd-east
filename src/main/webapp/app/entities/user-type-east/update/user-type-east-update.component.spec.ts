import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UserTypeEastFormService } from './user-type-east-form.service';
import { UserTypeEastService } from '../service/user-type-east.service';
import { IUserTypeEast } from '../user-type-east.model';

import { UserTypeEastUpdateComponent } from './user-type-east-update.component';

describe('UserTypeEast Management Update Component', () => {
  let comp: UserTypeEastUpdateComponent;
  let fixture: ComponentFixture<UserTypeEastUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userTypeEastFormService: UserTypeEastFormService;
  let userTypeEastService: UserTypeEastService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UserTypeEastUpdateComponent],
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
      .overrideTemplate(UserTypeEastUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserTypeEastUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userTypeEastFormService = TestBed.inject(UserTypeEastFormService);
    userTypeEastService = TestBed.inject(UserTypeEastService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const userTypeEast: IUserTypeEast = { id: 456 };

      activatedRoute.data = of({ userTypeEast });
      comp.ngOnInit();

      expect(comp.userTypeEast).toEqual(userTypeEast);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserTypeEast>>();
      const userTypeEast = { id: 123 };
      jest.spyOn(userTypeEastFormService, 'getUserTypeEast').mockReturnValue(userTypeEast);
      jest.spyOn(userTypeEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userTypeEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userTypeEast }));
      saveSubject.complete();

      // THEN
      expect(userTypeEastFormService.getUserTypeEast).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(userTypeEastService.update).toHaveBeenCalledWith(expect.objectContaining(userTypeEast));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserTypeEast>>();
      const userTypeEast = { id: 123 };
      jest.spyOn(userTypeEastFormService, 'getUserTypeEast').mockReturnValue({ id: null });
      jest.spyOn(userTypeEastService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userTypeEast: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userTypeEast }));
      saveSubject.complete();

      // THEN
      expect(userTypeEastFormService.getUserTypeEast).toHaveBeenCalled();
      expect(userTypeEastService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserTypeEast>>();
      const userTypeEast = { id: 123 };
      jest.spyOn(userTypeEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userTypeEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userTypeEastService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
