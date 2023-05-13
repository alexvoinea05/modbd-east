import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAddressEast } from '../address-east.model';
import { AddressEastService } from '../service/address-east.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './address-east-delete-dialog.component.html',
})
export class AddressEastDeleteDialogComponent {
  addressEast?: IAddressEast;

  constructor(protected addressEastService: AddressEastService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.addressEastService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
