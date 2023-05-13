import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TrainEastFormService } from './train-east-form.service';
import { TrainEastService } from '../service/train-east.service';
import { ITrainEast } from '../train-east.model';

import { TrainEastUpdateComponent } from './train-east-update.component';

describe('TrainEast Management Update Component', () => {
  let comp: TrainEastUpdateComponent;
  let fixture: ComponentFixture<TrainEastUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let trainEastFormService: TrainEastFormService;
  let trainEastService: TrainEastService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TrainEastUpdateComponent],
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
      .overrideTemplate(TrainEastUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TrainEastUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    trainEastFormService = TestBed.inject(TrainEastFormService);
    trainEastService = TestBed.inject(TrainEastService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const trainEast: ITrainEast = { id: 456 };

      activatedRoute.data = of({ trainEast });
      comp.ngOnInit();

      expect(comp.trainEast).toEqual(trainEast);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrainEast>>();
      const trainEast = { id: 123 };
      jest.spyOn(trainEastFormService, 'getTrainEast').mockReturnValue(trainEast);
      jest.spyOn(trainEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trainEast }));
      saveSubject.complete();

      // THEN
      expect(trainEastFormService.getTrainEast).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(trainEastService.update).toHaveBeenCalledWith(expect.objectContaining(trainEast));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrainEast>>();
      const trainEast = { id: 123 };
      jest.spyOn(trainEastFormService, 'getTrainEast').mockReturnValue({ id: null });
      jest.spyOn(trainEastService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainEast: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trainEast }));
      saveSubject.complete();

      // THEN
      expect(trainEastFormService.getTrainEast).toHaveBeenCalled();
      expect(trainEastService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrainEast>>();
      const trainEast = { id: 123 };
      jest.spyOn(trainEastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainEast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(trainEastService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
