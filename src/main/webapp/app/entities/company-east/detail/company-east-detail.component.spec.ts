import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CompanyEastDetailComponent } from './company-east-detail.component';

describe('CompanyEast Management Detail Component', () => {
  let comp: CompanyEastDetailComponent;
  let fixture: ComponentFixture<CompanyEastDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompanyEastDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ companyEast: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CompanyEastDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CompanyEastDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load companyEast on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.companyEast).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
