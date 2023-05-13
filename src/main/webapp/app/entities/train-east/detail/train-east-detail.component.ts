import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainEast } from '../train-east.model';

@Component({
  selector: 'jhi-train-east-detail',
  templateUrl: './train-east-detail.component.html',
})
export class TrainEastDetailComponent implements OnInit {
  trainEast: ITrainEast | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainEast }) => {
      this.trainEast = trainEast;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
