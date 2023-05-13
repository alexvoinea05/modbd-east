import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DistrictEastDetailComponent } from './district-east-detail.component';

describe('DistrictEast Management Detail Component', () => {
  let comp: DistrictEastDetailComponent;
  let fixture: ComponentFixture<DistrictEastDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DistrictEastDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ districtEast: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DistrictEastDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DistrictEastDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load districtEast on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.districtEast).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
