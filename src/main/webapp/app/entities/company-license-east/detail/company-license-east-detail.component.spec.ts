import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CompanyLicenseEastDetailComponent } from './company-license-east-detail.component';

describe('CompanyLicenseEast Management Detail Component', () => {
  let comp: CompanyLicenseEastDetailComponent;
  let fixture: ComponentFixture<CompanyLicenseEastDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompanyLicenseEastDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ companyLicenseEast: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CompanyLicenseEastDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CompanyLicenseEastDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load companyLicenseEast on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.companyLicenseEast).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
