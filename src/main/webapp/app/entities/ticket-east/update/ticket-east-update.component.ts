import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TicketEastFormService, TicketEastFormGroup } from './ticket-east-form.service';
import { ITicketEast } from '../ticket-east.model';
import { TicketEastService } from '../service/ticket-east.service';

@Component({
  selector: 'jhi-ticket-east-update',
  templateUrl: './ticket-east-update.component.html',
})
export class TicketEastUpdateComponent implements OnInit {
  isSaving = false;
  ticketEast: ITicketEast | null = null;

  editForm: TicketEastFormGroup = this.ticketEastFormService.createTicketEastFormGroup();

  constructor(
    protected ticketEastService: TicketEastService,
    protected ticketEastFormService: TicketEastFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ticketEast }) => {
      this.ticketEast = ticketEast;
      if (ticketEast) {
        this.updateForm(ticketEast);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ticketEast = this.ticketEastFormService.getTicketEast(this.editForm);
    if (ticketEast.id !== null) {
      this.subscribeToSaveResponse(this.ticketEastService.update(ticketEast));
    } else {
      this.subscribeToSaveResponse(this.ticketEastService.create(ticketEast));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITicketEast>>): void {
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

  protected updateForm(ticketEast: ITicketEast): void {
    this.ticketEast = ticketEast;
    this.ticketEastFormService.resetForm(this.editForm, ticketEast);
  }
}
