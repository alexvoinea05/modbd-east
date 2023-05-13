import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserTypeEastDetailComponent } from './user-type-east-detail.component';

describe('UserTypeEast Management Detail Component', () => {
  let comp: UserTypeEastDetailComponent;
  let fixture: ComponentFixture<UserTypeEastDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserTypeEastDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ userTypeEast: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UserTypeEastDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UserTypeEastDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load userTypeEast on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.userTypeEast).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
