import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AppUserEastFormService, AppUserEastFormGroup } from './app-user-east-form.service';
import { IAppUserEast } from '../app-user-east.model';
import { AppUserEastService } from '../service/app-user-east.service';

@Component({
  selector: 'jhi-app-user-east-update',
  templateUrl: './app-user-east-update.component.html',
})
export class AppUserEastUpdateComponent implements OnInit {
  isSaving = false;
  appUserEast: IAppUserEast | null = null;

  editForm: AppUserEastFormGroup = this.appUserEastFormService.createAppUserEastFormGroup();

  constructor(
    protected appUserEastService: AppUserEastService,
    protected appUserEastFormService: AppUserEastFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appUserEast }) => {
      this.appUserEast = appUserEast;
      if (appUserEast) {
        this.updateForm(appUserEast);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const appUserEast = this.appUserEastFormService.getAppUserEast(this.editForm);
    if (appUserEast.id !== null) {
      this.subscribeToSaveResponse(this.appUserEastService.update(appUserEast));
    } else {
      this.subscribeToSaveResponse(this.appUserEastService.create(appUserEast));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppUserEast>>): void {
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

  protected updateForm(appUserEast: IAppUserEast): void {
    this.appUserEast = appUserEast;
    this.appUserEastFormService.resetForm(this.editForm, appUserEast);
  }
}
