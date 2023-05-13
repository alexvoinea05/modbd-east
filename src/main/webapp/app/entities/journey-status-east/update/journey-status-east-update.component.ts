import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { JourneyStatusEastFormService, JourneyStatusEastFormGroup } from './journey-status-east-form.service';
import { IJourneyStatusEast } from '../journey-status-east.model';
import { JourneyStatusEastService } from '../service/journey-status-east.service';

@Component({
  selector: 'jhi-journey-status-east-update',
  templateUrl: './journey-status-east-update.component.html',
})
export class JourneyStatusEastUpdateComponent implements OnInit {
  isSaving = false;
  journeyStatusEast: IJourneyStatusEast | null = null;

  editForm: JourneyStatusEastFormGroup = this.journeyStatusEastFormService.createJourneyStatusEastFormGroup();

  constructor(
    protected journeyStatusEastService: JourneyStatusEastService,
    protected journeyStatusEastFormService: JourneyStatusEastFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ journeyStatusEast }) => {
      this.journeyStatusEast = journeyStatusEast;
      if (journeyStatusEast) {
        this.updateForm(journeyStatusEast);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const journeyStatusEast = this.journeyStatusEastFormService.getJourneyStatusEast(this.editForm);
    if (journeyStatusEast.id !== null) {
      this.subscribeToSaveResponse(this.journeyStatusEastService.update(journeyStatusEast));
    } else {
      this.subscribeToSaveResponse(this.journeyStatusEastService.create(journeyStatusEast));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJourneyStatusEast>>): void {
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

  protected updateForm(journeyStatusEast: IJourneyStatusEast): void {
    this.journeyStatusEast = journeyStatusEast;
    this.journeyStatusEastFormService.resetForm(this.editForm, journeyStatusEast);
  }
}
