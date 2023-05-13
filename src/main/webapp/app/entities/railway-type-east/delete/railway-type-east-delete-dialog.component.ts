import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRailwayTypeEast } from '../railway-type-east.model';
import { RailwayTypeEastService } from '../service/railway-type-east.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './railway-type-east-delete-dialog.component.html',
})
export class RailwayTypeEastDeleteDialogComponent {
  railwayTypeEast?: IRailwayTypeEast;

  constructor(protected railwayTypeEastService: RailwayTypeEastService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.railwayTypeEastService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
