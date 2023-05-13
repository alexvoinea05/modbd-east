import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRailwayTypeEast } from '../railway-type-east.model';

@Component({
  selector: 'jhi-railway-type-east-detail',
  templateUrl: './railway-type-east-detail.component.html',
})
export class RailwayTypeEastDetailComponent implements OnInit {
  railwayTypeEast: IRailwayTypeEast | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ railwayTypeEast }) => {
      this.railwayTypeEast = railwayTypeEast;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
