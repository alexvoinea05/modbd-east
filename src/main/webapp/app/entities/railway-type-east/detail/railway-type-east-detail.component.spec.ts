import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RailwayTypeEastDetailComponent } from './railway-type-east-detail.component';

describe('RailwayTypeEast Management Detail Component', () => {
  let comp: RailwayTypeEastDetailComponent;
  let fixture: ComponentFixture<RailwayTypeEastDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RailwayTypeEastDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ railwayTypeEast: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RailwayTypeEastDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RailwayTypeEastDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load railwayTypeEast on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.railwayTypeEast).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
