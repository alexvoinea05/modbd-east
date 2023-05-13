import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompanyLicenseEast } from '../company-license-east.model';
import { CompanyLicenseEastService } from '../service/company-license-east.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './company-license-east-delete-dialog.component.html',
})
export class CompanyLicenseEastDeleteDialogComponent {
  companyLicenseEast?: ICompanyLicenseEast;

  constructor(protected companyLicenseEastService: CompanyLicenseEastService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.companyLicenseEastService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
