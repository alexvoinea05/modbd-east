import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TicketEastDetailComponent } from './ticket-east-detail.component';

describe('TicketEast Management Detail Component', () => {
  let comp: TicketEastDetailComponent;
  let fixture: ComponentFixture<TicketEastDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TicketEastDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ticketEast: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TicketEastDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TicketEastDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ticketEast on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ticketEast).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
