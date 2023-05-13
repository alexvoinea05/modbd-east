import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJourneyEast } from '../journey-east.model';

@Component({
  selector: 'jhi-journey-east-detail',
  templateUrl: './journey-east-detail.component.html',
})
export class JourneyEastDetailComponent implements OnInit {
  journeyEast: IJourneyEast | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ journeyEast }) => {
      this.journeyEast = journeyEast;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
