import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AddressEastFormService } from './address-east-form.service';
import { AddressEastService } from '../service/address-east.service';
import { IAddressEast } from '../address-east.model';

import { AddressEastUpdateComponent } from './address-east-update.component';

describe('AddressEast Management Update Component', () => {
  let comp: AddressEastUpdateComponent;
  let fixture: ComponentFixture<AddressEastUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let addressEastFormService: AddressEastFormService;
  let addressEastService: AddressEastService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AddressEastUpdateComponent],
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
      .overrideTemplate(AddressEastUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AddressEastUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    addressEastFormService = TestBed.inject(AddressEastFormService);
    addressEastService = TestBed.inject(AddressEastService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const addressEast: IAddressEast = { id: 456 };

      activatedRoute.data = of({ addressEast });
      comp.ngOnInit();

      expect(comp.addressEast).toEqual(addressEast);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddressEast>>();
      const addressEast = { id: 123 };
      jest.spyOn(addressEastFormService, 'getAddressEast').mockReturnValue(addressEast);
      jest.spyOn(addressEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addressEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: addressEast }));
      saveSubject.complete();

      // THEN
      expect(addressEastFormService.getAddressEast).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(addressEastService.update).toHaveBeenCalledWith(expect.objectContaining(addressEast));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddressEast>>();
      const addressEast = { id: 123 };
      jest.spyOn(addressEastFormService, 'getAddressEast').mockReturnValue({ id: null });
      jest.spyOn(addressEastService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addressEast: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: addressEast }));
      saveSubject.complete();

      // THEN
      expect(addressEastFormService.getAddressEast).toHaveBeenCalled();
      expect(addressEastService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddressEast>>();
      const addressEast = { id: 123 };
      jest.spyOn(addressEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addressEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(addressEastService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
