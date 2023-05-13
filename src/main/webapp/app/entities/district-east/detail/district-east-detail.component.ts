import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDistrictEast } from '../district-east.model';

@Component({
  selector: 'jhi-district-east-detail',
  templateUrl: './district-east-detail.component.html',
})
export class DistrictEastDetailComponent implements OnInit {
  districtEast: IDistrictEast | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ districtEast }) => {
      this.districtEast = districtEast;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
