import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AddressEastDetailComponent } from './address-east-detail.component';

describe('AddressEast Management Detail Component', () => {
  let comp: AddressEastDetailComponent;
  let fixture: ComponentFixture<AddressEastDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddressEastDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ addressEast: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AddressEastDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AddressEastDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load addressEast on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.addressEast).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
