import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LicenseEastDetailComponent } from './license-east-detail.component';

describe('LicenseEast Management Detail Component', () => {
  let comp: LicenseEastDetailComponent;
  let fixture: ComponentFixture<LicenseEastDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LicenseEastDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ licenseEast: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LicenseEastDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LicenseEastDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load licenseEast on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.licenseEast).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
