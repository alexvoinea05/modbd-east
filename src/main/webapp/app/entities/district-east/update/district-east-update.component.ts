import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DistrictEastFormService, DistrictEastFormGroup } from './district-east-form.service';
import { IDistrictEast } from '../district-east.model';
import { DistrictEastService } from '../service/district-east.service';

@Component({
  selector: 'jhi-district-east-update',
  templateUrl: './district-east-update.component.html',
})
export class DistrictEastUpdateComponent implements OnInit {
  isSaving = false;
  districtEast: IDistrictEast | null = null;

  editForm: DistrictEastFormGroup = this.districtEastFormService.createDistrictEastFormGroup();

  constructor(
    protected districtEastService: DistrictEastService,
    protected districtEastFormService: DistrictEastFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ districtEast }) => {
      this.districtEast = districtEast;
      if (districtEast) {
        this.updateForm(districtEast);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const districtEast = this.districtEastFormService.getDistrictEast(this.editForm);
    if (districtEast.id !== null) {
      this.subscribeToSaveResponse(this.districtEastService.update(districtEast));
    } else {
      this.subscribeToSaveResponse(this.districtEastService.create(districtEast));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDistrictEast>>): void {
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

  protected updateForm(districtEast: IDistrictEast): void {
    this.districtEast = districtEast;
    this.districtEastFormService.resetForm(this.editForm, districtEast);
  }
}
