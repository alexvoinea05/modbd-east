import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CityEastFormService, CityEastFormGroup } from './city-east-form.service';
import { ICityEast } from '../city-east.model';
import { CityEastService } from '../service/city-east.service';

@Component({
  selector: 'jhi-city-east-update',
  templateUrl: './city-east-update.component.html',
})
export class CityEastUpdateComponent implements OnInit {
  isSaving = false;
  cityEast: ICityEast | null = null;

  editForm: CityEastFormGroup = this.cityEastFormService.createCityEastFormGroup();

  constructor(
    protected cityEastService: CityEastService,
    protected cityEastFormService: CityEastFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cityEast }) => {
      this.cityEast = cityEast;
      if (cityEast) {
        this.updateForm(cityEast);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cityEast = this.cityEastFormService.getCityEast(this.editForm);
    if (cityEast.id !== null) {
      this.subscribeToSaveResponse(this.cityEastService.update(cityEast));
    } else {
      this.subscribeToSaveResponse(this.cityEastService.create(cityEast));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICityEast>>): void {
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

  protected updateForm(cityEast: ICityEast): void {
    this.cityEast = cityEast;
    this.cityEastFormService.resetForm(this.editForm, cityEast);
  }
}
