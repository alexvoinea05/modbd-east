import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrainTypeEastDetailComponent } from './train-type-east-detail.component';

describe('TrainTypeEast Management Detail Component', () => {
  let comp: TrainTypeEastDetailComponent;
  let fixture: ComponentFixture<TrainTypeEastDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrainTypeEastDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ trainTypeEast: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TrainTypeEastDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TrainTypeEastDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load trainTypeEast on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.trainTypeEast).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
