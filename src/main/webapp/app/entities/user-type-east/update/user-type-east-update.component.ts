import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { UserTypeEastFormService, UserTypeEastFormGroup } from './user-type-east-form.service';
import { IUserTypeEast } from '../user-type-east.model';
import { UserTypeEastService } from '../service/user-type-east.service';

@Component({
  selector: 'jhi-user-type-east-update',
  templateUrl: './user-type-east-update.component.html',
})
export class UserTypeEastUpdateComponent implements OnInit {
  isSaving = false;
  userTypeEast: IUserTypeEast | null = null;

  editForm: UserTypeEastFormGroup = this.userTypeEastFormService.createUserTypeEastFormGroup();

  constructor(
    protected userTypeEastService: UserTypeEastService,
    protected userTypeEastFormService: UserTypeEastFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userTypeEast }) => {
      this.userTypeEast = userTypeEast;
      if (userTypeEast) {
        this.updateForm(userTypeEast);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userTypeEast = this.userTypeEastFormService.getUserTypeEast(this.editForm);
    if (userTypeEast.id !== null) {
      this.subscribeToSaveResponse(this.userTypeEastService.update(userTypeEast));
    } else {
      this.subscribeToSaveResponse(this.userTypeEastService.create(userTypeEast));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserTypeEast>>): void {
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

  protected updateForm(userTypeEast: IUserTypeEast): void {
    this.userTypeEast = userTypeEast;
    this.userTypeEastFormService.resetForm(this.editForm, userTypeEast);
  }
}
