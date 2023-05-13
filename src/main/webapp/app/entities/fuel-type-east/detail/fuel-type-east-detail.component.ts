import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFuelTypeEast } from '../fuel-type-east.model';

@Component({
  selector: 'jhi-fuel-type-east-detail',
  templateUrl: './fuel-type-east-detail.component.html',
})
export class FuelTypeEastDetailComponent implements OnInit {
  fuelTypeEast: IFuelTypeEast | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fuelTypeEast }) => {
      this.fuelTypeEast = fuelTypeEast;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
