import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JourneyStatusEastDetailComponent } from './journey-status-east-detail.component';

describe('JourneyStatusEast Management Detail Component', () => {
  let comp: JourneyStatusEastDetailComponent;
  let fixture: ComponentFixture<JourneyStatusEastDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [JourneyStatusEastDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ journeyStatusEast: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(JourneyStatusEastDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(JourneyStatusEastDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load journeyStatusEast on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.journeyStatusEast).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
