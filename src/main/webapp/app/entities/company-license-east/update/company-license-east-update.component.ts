import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CompanyLicenseEastFormService, CompanyLicenseEastFormGroup } from './company-license-east-form.service';
import { ICompanyLicenseEast } from '../company-license-east.model';
import { CompanyLicenseEastService } from '../service/company-license-east.service';
import { ICompanyEast } from 'app/entities/company-east/company-east.model';
import { CompanyEastService } from 'app/entities/company-east/service/company-east.service';
import { ILicenseEast } from 'app/entities/license-east/license-east.model';
import { LicenseEastService } from 'app/entities/license-east/service/license-east.service';

@Component({
  selector: 'jhi-company-license-east-update',
  templateUrl: './company-license-east-update.component.html',
})
export class CompanyLicenseEastUpdateComponent implements OnInit {
  isSaving = false;
  companyLicenseEast: ICompanyLicenseEast | null = null;

  companyEastsSharedCollection: ICompanyEast[] = [];
  licenseEastsSharedCollection: ILicenseEast[] = [];

  editForm: CompanyLicenseEastFormGroup = this.companyLicenseEastFormService.createCompanyLicenseEastFormGroup();

  constructor(
    protected companyLicenseEastService: CompanyLicenseEastService,
    protected companyLicenseEastFormService: CompanyLicenseEastFormService,
    protected companyEastService: CompanyEastService,
    protected licenseEastService: LicenseEastService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCompanyEast = (o1: ICompanyEast | null, o2: ICompanyEast | null): boolean => this.companyEastService.compareCompanyEast(o1, o2);

  compareLicenseEast = (o1: ILicenseEast | null, o2: ILicenseEast | null): boolean => this.licenseEastService.compareLicenseEast(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyLicenseEast }) => {
      this.companyLicenseEast = companyLicenseEast;
      if (companyLicenseEast) {
        this.updateForm(companyLicenseEast);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const companyLicenseEast = this.companyLicenseEastFormService.getCompanyLicenseEast(this.editForm);
    if (companyLicenseEast.id !== null) {
      this.subscribeToSaveResponse(this.companyLicenseEastService.update(companyLicenseEast));
    } else {
      this.subscribeToSaveResponse(this.companyLicenseEastService.create(companyLicenseEast));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyLicenseEast>>): void {
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

  protected updateForm(companyLicenseEast: ICompanyLicenseEast): void {
    this.companyLicenseEast = companyLicenseEast;
    this.companyLicenseEastFormService.resetForm(this.editForm, companyLicenseEast);

    this.companyEastsSharedCollection = this.companyEastService.addCompanyEastToCollectionIfMissing<ICompanyEast>(
      this.companyEastsSharedCollection,
      companyLicenseEast.idCompany
    );
    this.licenseEastsSharedCollection = this.licenseEastService.addLicenseEastToCollectionIfMissing<ILicenseEast>(
      this.licenseEastsSharedCollection,
      companyLicenseEast.idLicense
    );
  }

  protected loadRelationshipsOptions(): void {
    this.companyEastService
      .query()
      .pipe(map((res: HttpResponse<ICompanyEast[]>) => res.body ?? []))
      .pipe(
        map((companyEasts: ICompanyEast[]) =>
          this.companyEastService.addCompanyEastToCollectionIfMissing<ICompanyEast>(companyEasts, this.companyLicenseEast?.idCompany)
        )
      )
      .subscribe((companyEasts: ICompanyEast[]) => (this.companyEastsSharedCollection = companyEasts));

    this.licenseEastService
      .query()
      .pipe(map((res: HttpResponse<ILicenseEast[]>) => res.body ?? []))
      .pipe(
        map((licenseEasts: ILicenseEast[]) =>
          this.licenseEastService.addLicenseEastToCollectionIfMissing<ILicenseEast>(licenseEasts, this.companyLicenseEast?.idLicense)
        )
      )
      .subscribe((licenseEasts: ILicenseEast[]) => (this.licenseEastsSharedCollection = licenseEasts));
  }
}
