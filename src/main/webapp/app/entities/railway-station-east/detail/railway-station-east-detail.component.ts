import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRailwayStationEast } from '../railway-station-east.model';

@Component({
  selector: 'jhi-railway-station-east-detail',
  templateUrl: './railway-station-east-detail.component.html',
})
export class RailwayStationEastDetailComponent implements OnInit {
  railwayStationEast: IRailwayStationEast | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ railwayStationEast }) => {
      this.railwayStationEast = railwayStationEast;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
