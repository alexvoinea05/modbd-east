import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IJourneyEast } from '../journey-east.model';
import { JourneyEastService } from '../service/journey-east.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './journey-east-delete-dialog.component.html',
})
export class JourneyEastDeleteDialogComponent {
  journeyEast?: IJourneyEast;

  constructor(protected journeyEastService: JourneyEastService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.journeyEastService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
