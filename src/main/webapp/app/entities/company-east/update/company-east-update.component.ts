import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CompanyEastFormService, CompanyEastFormGroup } from './company-east-form.service';
import { ICompanyEast } from '../company-east.model';
import { CompanyEastService } from '../service/company-east.service';

@Component({
  selector: 'jhi-company-east-update',
  templateUrl: './company-east-update.component.html',
})
export class CompanyEastUpdateComponent implements OnInit {
  isSaving = false;
  companyEast: ICompanyEast | null = null;

  editForm: CompanyEastFormGroup = this.companyEastFormService.createCompanyEastFormGroup();

  constructor(
    protected companyEastService: CompanyEastService,
    protected companyEastFormService: CompanyEastFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyEast }) => {
      this.companyEast = companyEast;
      if (companyEast) {
        this.updateForm(companyEast);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const companyEast = this.companyEastFormService.getCompanyEast(this.editForm);
    if (companyEast.id !== null) {
      this.subscribeToSaveResponse(this.companyEastService.update(companyEast));
    } else {
      this.subscribeToSaveResponse(this.companyEastService.create(companyEast));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyEast>>): void {
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

  protected updateForm(companyEast: ICompanyEast): void {
    this.companyEast = companyEast;
    this.companyEastFormService.resetForm(this.editForm, companyEast);
  }
}
