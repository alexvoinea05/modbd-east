import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRailwayStationEast } from '../railway-station-east.model';
import { RailwayStationEastService } from '../service/railway-station-east.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './railway-station-east-delete-dialog.component.html',
})
export class RailwayStationEastDeleteDialogComponent {
  railwayStationEast?: IRailwayStationEast;

  constructor(protected railwayStationEastService: RailwayStationEastService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.railwayStationEastService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
