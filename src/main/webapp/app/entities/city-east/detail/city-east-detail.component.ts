import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICityEast } from '../city-east.model';

@Component({
  selector: 'jhi-city-east-detail',
  templateUrl: './city-east-detail.component.html',
})
export class CityEastDetailComponent implements OnInit {
  cityEast: ICityEast | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cityEast }) => {
      this.cityEast = cityEast;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
