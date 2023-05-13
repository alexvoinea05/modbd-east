import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrainEastDetailComponent } from './train-east-detail.component';

describe('TrainEast Management Detail Component', () => {
  let comp: TrainEastDetailComponent;
  let fixture: ComponentFixture<TrainEastDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrainEastDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ trainEast: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TrainEastDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TrainEastDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load trainEast on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.trainEast).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
