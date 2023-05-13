import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JourneyEastDetailComponent } from './journey-east-detail.component';

describe('JourneyEast Management Detail Component', () => {
  let comp: JourneyEastDetailComponent;
  let fixture: ComponentFixture<JourneyEastDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [JourneyEastDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ journeyEast: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(JourneyEastDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(JourneyEastDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load journeyEast on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.journeyEast).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
