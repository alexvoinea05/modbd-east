import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITicketEast } from '../ticket-east.model';

@Component({
  selector: 'jhi-ticket-east-detail',
  templateUrl: './ticket-east-detail.component.html',
})
export class TicketEastDetailComponent implements OnInit {
  ticketEast: ITicketEast | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ticketEast }) => {
      this.ticketEast = ticketEast;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
