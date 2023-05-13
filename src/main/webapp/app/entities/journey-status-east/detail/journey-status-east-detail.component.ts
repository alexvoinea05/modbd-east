import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJourneyStatusEast } from '../journey-status-east.model';

@Component({
  selector: 'jhi-journey-status-east-detail',
  templateUrl: './journey-status-east-detail.component.html',
})
export class JourneyStatusEastDetailComponent implements OnInit {
  journeyStatusEast: IJourneyStatusEast | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ journeyStatusEast }) => {
      this.journeyStatusEast = journeyStatusEast;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
