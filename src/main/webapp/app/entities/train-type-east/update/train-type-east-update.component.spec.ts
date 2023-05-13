import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TrainTypeEastFormService } from './train-type-east-form.service';
import { TrainTypeEastService } from '../service/train-type-east.service';
import { ITrainTypeEast } from '../train-type-east.model';

import { TrainTypeEastUpdateComponent } from './train-type-east-update.component';

describe('TrainTypeEast Management Update Component', () => {
  let comp: TrainTypeEastUpdateComponent;
  let fixture: ComponentFixture<TrainTypeEastUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let trainTypeEastFormService: TrainTypeEastFormService;
  let trainTypeEastService: TrainTypeEastService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TrainTypeEastUpdateComponent],
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
      .overrideTemplate(TrainTypeEastUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TrainTypeEastUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    trainTypeEastFormService = TestBed.inject(TrainTypeEastFormService);
    trainTypeEastService = TestBed.inject(TrainTypeEastService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const trainTypeEast: ITrainTypeEast = { id: 456 };

      activatedRoute.data = of({ trainTypeEast });
      comp.ngOnInit();

      expect(comp.trainTypeEast).toEqual(trainTypeEast);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrainTypeEast>>();
      const trainTypeEast = { id: 123 };
      jest.spyOn(trainTypeEastFormService, 'getTrainTypeEast').mockReturnValue(trainTypeEast);
      jest.spyOn(trainTypeEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainTypeEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trainTypeEast }));
      saveSubject.complete();

      // THEN
      expect(trainTypeEastFormService.getTrainTypeEast).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(trainTypeEastService.update).toHaveBeenCalledWith(expect.objectContaining(trainTypeEast));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrainTypeEast>>();
      const trainTypeEast = { id: 123 };
      jest.spyOn(trainTypeEastFormService, 'getTrainTypeEast').mockReturnValue({ id: null });
      jest.spyOn(trainTypeEastService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainTypeEast: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trainTypeEast }));
      saveSubject.complete();

      // THEN
      expect(trainTypeEastFormService.getTrainTypeEast).toHaveBeenCalled();
      expect(trainTypeEastService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrainTypeEast>>();
      const trainTypeEast = { id: 123 };
      jest.spyOn(trainTypeEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainTypeEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(trainTypeEastService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
