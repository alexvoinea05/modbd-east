import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TrainEastFormService, TrainEastFormGroup } from './train-east-form.service';
import { ITrainEast } from '../train-east.model';
import { TrainEastService } from '../service/train-east.service';

@Component({
  selector: 'jhi-train-east-update',
  templateUrl: './train-east-update.component.html',
})
export class TrainEastUpdateComponent implements OnInit {
  isSaving = false;
  trainEast: ITrainEast | null = null;

  editForm: TrainEastFormGroup = this.trainEastFormService.createTrainEastFormGroup();

  constructor(
    protected trainEastService: TrainEastService,
    protected trainEastFormService: TrainEastFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainEast }) => {
      this.trainEast = trainEast;
      if (trainEast) {
        this.updateForm(trainEast);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trainEast = this.trainEastFormService.getTrainEast(this.editForm);
    if (trainEast.id !== null) {
      this.subscribeToSaveResponse(this.trainEastService.update(trainEast));
    } else {
      this.subscribeToSaveResponse(this.trainEastService.create(trainEast));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainEast>>): void {
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

  protected updateForm(trainEast: ITrainEast): void {
    this.trainEast = trainEast;
    this.trainEastFormService.resetForm(this.editForm, trainEast);
  }
}
