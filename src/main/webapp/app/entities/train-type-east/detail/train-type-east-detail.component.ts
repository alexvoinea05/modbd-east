import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainTypeEast } from '../train-type-east.model';

@Component({
  selector: 'jhi-train-type-east-detail',
  templateUrl: './train-type-east-detail.component.html',
})
export class TrainTypeEastDetailComponent implements OnInit {
  trainTypeEast: ITrainTypeEast | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainTypeEast }) => {
      this.trainTypeEast = trainTypeEast;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
