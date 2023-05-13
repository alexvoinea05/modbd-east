import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { RailwayTypeEastFormService, RailwayTypeEastFormGroup } from './railway-type-east-form.service';
import { IRailwayTypeEast } from '../railway-type-east.model';
import { RailwayTypeEastService } from '../service/railway-type-east.service';

@Component({
  selector: 'jhi-railway-type-east-update',
  templateUrl: './railway-type-east-update.component.html',
})
export class RailwayTypeEastUpdateComponent implements OnInit {
  isSaving = false;
  railwayTypeEast: IRailwayTypeEast | null = null;

  editForm: RailwayTypeEastFormGroup = this.railwayTypeEastFormService.createRailwayTypeEastFormGroup();

  constructor(
    protected railwayTypeEastService: RailwayTypeEastService,
    protected railwayTypeEastFormService: RailwayTypeEastFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ railwayTypeEast }) => {
      this.railwayTypeEast = railwayTypeEast;
      if (railwayTypeEast) {
        this.updateForm(railwayTypeEast);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const railwayTypeEast = this.railwayTypeEastFormService.getRailwayTypeEast(this.editForm);
    if (railwayTypeEast.id !== null) {
      this.subscribeToSaveResponse(this.railwayTypeEastService.update(railwayTypeEast));
    } else {
      this.subscribeToSaveResponse(this.railwayTypeEastService.create(railwayTypeEast));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRailwayTypeEast>>): void {
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

  protected updateForm(railwayTypeEast: IRailwayTypeEast): void {
    this.railwayTypeEast = railwayTypeEast;
    this.railwayTypeEastFormService.resetForm(this.editForm, railwayTypeEast);
  }
}
