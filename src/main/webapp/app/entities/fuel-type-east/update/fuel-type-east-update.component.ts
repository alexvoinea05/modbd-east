import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FuelTypeEastFormService, FuelTypeEastFormGroup } from './fuel-type-east-form.service';
import { IFuelTypeEast } from '../fuel-type-east.model';
import { FuelTypeEastService } from '../service/fuel-type-east.service';

@Component({
  selector: 'jhi-fuel-type-east-update',
  templateUrl: './fuel-type-east-update.component.html',
})
export class FuelTypeEastUpdateComponent implements OnInit {
  isSaving = false;
  fuelTypeEast: IFuelTypeEast | null = null;

  editForm: FuelTypeEastFormGroup = this.fuelTypeEastFormService.createFuelTypeEastFormGroup();

  constructor(
    protected fuelTypeEastService: FuelTypeEastService,
    protected fuelTypeEastFormService: FuelTypeEastFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fuelTypeEast }) => {
      this.fuelTypeEast = fuelTypeEast;
      if (fuelTypeEast) {
        this.updateForm(fuelTypeEast);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fuelTypeEast = this.fuelTypeEastFormService.getFuelTypeEast(this.editForm);
    if (fuelTypeEast.id !== null) {
      this.subscribeToSaveResponse(this.fuelTypeEastService.update(fuelTypeEast));
    } else {
      this.subscribeToSaveResponse(this.fuelTypeEastService.create(fuelTypeEast));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuelTypeEast>>): void {
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

  protected updateForm(fuelTypeEast: IFuelTypeEast): void {
    this.fuelTypeEast = fuelTypeEast;
    this.fuelTypeEastFormService.resetForm(this.editForm, fuelTypeEast);
  }
}
