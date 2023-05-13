import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { RailwayStationEastFormService, RailwayStationEastFormGroup } from './railway-station-east-form.service';
import { IRailwayStationEast } from '../railway-station-east.model';
import { RailwayStationEastService } from '../service/railway-station-east.service';

@Component({
  selector: 'jhi-railway-station-east-update',
  templateUrl: './railway-station-east-update.component.html',
})
export class RailwayStationEastUpdateComponent implements OnInit {
  isSaving = false;
  railwayStationEast: IRailwayStationEast | null = null;

  editForm: RailwayStationEastFormGroup = this.railwayStationEastFormService.createRailwayStationEastFormGroup();

  constructor(
    protected railwayStationEastService: RailwayStationEastService,
    protected railwayStationEastFormService: RailwayStationEastFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ railwayStationEast }) => {
      this.railwayStationEast = railwayStationEast;
      if (railwayStationEast) {
        this.updateForm(railwayStationEast);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const railwayStationEast = this.railwayStationEastFormService.getRailwayStationEast(this.editForm);
    if (railwayStationEast.id !== null) {
      this.subscribeToSaveResponse(this.railwayStationEastService.update(railwayStationEast));
    } else {
      this.subscribeToSaveResponse(this.railwayStationEastService.create(railwayStationEast));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRailwayStationEast>>): void {
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

  protected updateForm(railwayStationEast: IRailwayStationEast): void {
    this.railwayStationEast = railwayStationEast;
    this.railwayStationEastFormService.resetForm(this.editForm, railwayStationEast);
  }
}
