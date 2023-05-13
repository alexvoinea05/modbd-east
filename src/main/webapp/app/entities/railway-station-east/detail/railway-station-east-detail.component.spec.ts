import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RailwayStationEastDetailComponent } from './railway-station-east-detail.component';

describe('RailwayStationEast Management Detail Component', () => {
  let comp: RailwayStationEastDetailComponent;
  let fixture: ComponentFixture<RailwayStationEastDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RailwayStationEastDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ railwayStationEast: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RailwayStationEastDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RailwayStationEastDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load railwayStationEast on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.railwayStationEast).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
