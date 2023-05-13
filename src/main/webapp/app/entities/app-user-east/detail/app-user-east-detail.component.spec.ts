import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppUserEastDetailComponent } from './app-user-east-detail.component';

describe('AppUserEast Management Detail Component', () => {
  let comp: AppUserEastDetailComponent;
  let fixture: ComponentFixture<AppUserEastDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AppUserEastDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ appUserEast: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AppUserEastDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AppUserEastDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load appUserEast on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.appUserEast).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
