import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompanyEast } from '../company-east.model';
import { CompanyEastService } from '../service/company-east.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './company-east-delete-dialog.component.html',
})
export class CompanyEastDeleteDialogComponent {
  companyEast?: ICompanyEast;

  constructor(protected companyEastService: CompanyEastService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.companyEastService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
