import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFuelTypeEast } from '../fuel-type-east.model';
import { FuelTypeEastService } from '../service/fuel-type-east.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './fuel-type-east-delete-dialog.component.html',
})
export class FuelTypeEastDeleteDialogComponent {
  fuelTypeEast?: IFuelTypeEast;

  constructor(protected fuelTypeEastService: FuelTypeEastService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fuelTypeEastService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
