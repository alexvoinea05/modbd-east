import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IJourneyStatusEast } from '../journey-status-east.model';
import { JourneyStatusEastService } from '../service/journey-status-east.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './journey-status-east-delete-dialog.component.html',
})
export class JourneyStatusEastDeleteDialogComponent {
  journeyStatusEast?: IJourneyStatusEast;

  constructor(protected journeyStatusEastService: JourneyStatusEastService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.journeyStatusEastService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
