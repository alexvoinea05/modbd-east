import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { LicenseEastFormService, LicenseEastFormGroup } from './license-east-form.service';
import { ILicenseEast } from '../license-east.model';
import { LicenseEastService } from '../service/license-east.service';

@Component({
  selector: 'jhi-license-east-update',
  templateUrl: './license-east-update.component.html',
})
export class LicenseEastUpdateComponent implements OnInit {
  isSaving = false;
  licenseEast: ILicenseEast | null = null;

  editForm: LicenseEastFormGroup = this.licenseEastFormService.createLicenseEastFormGroup();

  constructor(
    protected licenseEastService: LicenseEastService,
    protected licenseEastFormService: LicenseEastFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ licenseEast }) => {
      this.licenseEast = licenseEast;
      if (licenseEast) {
        this.updateForm(licenseEast);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const licenseEast = this.licenseEastFormService.getLicenseEast(this.editForm);
    if (licenseEast.id !== null) {
      this.subscribeToSaveResponse(this.licenseEastService.update(licenseEast));
    } else {
      this.subscribeToSaveResponse(this.licenseEastService.create(licenseEast));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILicenseEast>>): void {
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

  protected updateForm(licenseEast: ILicenseEast): void {
    this.licenseEast = licenseEast;
    this.licenseEastFormService.resetForm(this.editForm, licenseEast);
  }
}
