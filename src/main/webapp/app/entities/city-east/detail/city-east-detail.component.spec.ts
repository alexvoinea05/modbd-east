import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CityEastDetailComponent } from './city-east-detail.component';

describe('CityEast Management Detail Component', () => {
  let comp: CityEastDetailComponent;
  let fixture: ComponentFixture<CityEastDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CityEastDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ cityEast: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CityEastDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CityEastDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cityEast on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.cityEast).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
