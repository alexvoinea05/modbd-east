import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { JourneyEastFormService, JourneyEastFormGroup } from './journey-east-form.service';
import { IJourneyEast } from '../journey-east.model';
import { JourneyEastService } from '../service/journey-east.service';

@Component({
  selector: 'jhi-journey-east-update',
  templateUrl: './journey-east-update.component.html',
})
export class JourneyEastUpdateComponent implements OnInit {
  isSaving = false;
  journeyEast: IJourneyEast | null = null;

  editForm: JourneyEastFormGroup = this.journeyEastFormService.createJourneyEastFormGroup();

  constructor(
    protected journeyEastService: JourneyEastService,
    protected journeyEastFormService: JourneyEastFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ journeyEast }) => {
      this.journeyEast = journeyEast;
      if (journeyEast) {
        this.updateForm(journeyEast);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const journeyEast = this.journeyEastFormService.getJourneyEast(this.editForm);
    if (journeyEast.id !== null) {
      this.subscribeToSaveResponse(this.journeyEastService.update(journeyEast));
    } else {
      this.subscribeToSaveResponse(this.journeyEastService.create(journeyEast));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJourneyEast>>): void {
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

  protected updateForm(journeyEast: IJourneyEast): void {
    this.journeyEast = journeyEast;
    this.journeyEastFormService.resetForm(this.editForm, journeyEast);
  }
}
