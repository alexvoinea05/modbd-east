import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AddressEastFormService, AddressEastFormGroup } from './address-east-form.service';
import { IAddressEast } from '../address-east.model';
import { AddressEastService } from '../service/address-east.service';

@Component({
  selector: 'jhi-address-east-update',
  templateUrl: './address-east-update.component.html',
})
export class AddressEastUpdateComponent implements OnInit {
  isSaving = false;
  addressEast: IAddressEast | null = null;

  editForm: AddressEastFormGroup = this.addressEastFormService.createAddressEastFormGroup();

  constructor(
    protected addressEastService: AddressEastService,
    protected addressEastFormService: AddressEastFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ addressEast }) => {
      this.addressEast = addressEast;
      if (addressEast) {
        this.updateForm(addressEast);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const addressEast = this.addressEastFormService.getAddressEast(this.editForm);
    if (addressEast.id !== null) {
      this.subscribeToSaveResponse(this.addressEastService.update(addressEast));
    } else {
      this.subscribeToSaveResponse(this.addressEastService.create(addressEast));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddressEast>>): void {
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

  protected updateForm(addressEast: IAddressEast): void {
    this.addressEast = addressEast;
    this.addressEastFormService.resetForm(this.editForm, addressEast);
  }
}
