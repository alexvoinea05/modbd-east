import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TrainTypeEastFormService, TrainTypeEastFormGroup } from './train-type-east-form.service';
import { ITrainTypeEast } from '../train-type-east.model';
import { TrainTypeEastService } from '../service/train-type-east.service';

@Component({
  selector: 'jhi-train-type-east-update',
  templateUrl: './train-type-east-update.component.html',
})
export class TrainTypeEastUpdateComponent implements OnInit {
  isSaving = false;
  trainTypeEast: ITrainTypeEast | null = null;

  editForm: TrainTypeEastFormGroup = this.trainTypeEastFormService.createTrainTypeEastFormGroup();

  constructor(
    protected trainTypeEastService: TrainTypeEastService,
    protected trainTypeEastFormService: TrainTypeEastFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainTypeEast }) => {
      this.trainTypeEast = trainTypeEast;
      if (trainTypeEast) {
        this.updateForm(trainTypeEast);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trainTypeEast = this.trainTypeEastFormService.getTrainTypeEast(this.editForm);
    if (trainTypeEast.id !== null) {
      this.subscribeToSaveResponse(this.trainTypeEastService.update(trainTypeEast));
    } else {
      this.subscribeToSaveResponse(this.trainTypeEastService.create(trainTypeEast));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainTypeEast>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(trainTypeEast: ITrainTypeEast): void {
    this.trainTypeEast = trainTypeEast;
    this.trainTypeEastFormService.resetForm(this.editForm, trainTypeEast);
  }
}
