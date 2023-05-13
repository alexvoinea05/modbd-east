import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILicenseEast } from '../license-east.model';
import { LicenseEastService } from '../service/license-east.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './license-east-delete-dialog.component.html',
})
export class LicenseEastDeleteDialogComponent {
  licenseEast?: ILicenseEast;

  constructor(protected licenseEastService: LicenseEastService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.licenseEastService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
