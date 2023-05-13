import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDistrictEast } from '../district-east.model';
import { DistrictEastService } from '../service/district-east.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './district-east-delete-dialog.component.html',
})
export class DistrictEastDeleteDialogComponent {
  districtEast?: IDistrictEast;

  constructor(protected districtEastService: DistrictEastService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.districtEastService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
