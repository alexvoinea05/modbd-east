import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FuelTypeEastDetailComponent } from './fuel-type-east-detail.component';

describe('FuelTypeEast Management Detail Component', () => {
  let comp: FuelTypeEastDetailComponent;
  let fixture: ComponentFixture<FuelTypeEastDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FuelTypeEastDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ fuelTypeEast: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FuelTypeEastDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FuelTypeEastDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load fuelTypeEast on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.fuelTypeEast).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
