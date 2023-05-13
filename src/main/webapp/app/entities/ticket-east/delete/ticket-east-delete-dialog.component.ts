import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITicketEast } from '../ticket-east.model';
import { TicketEastService } from '../service/ticket-east.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './ticket-east-delete-dialog.component.html',
})
export class TicketEastDeleteDialogComponent {
  ticketEast?: ITicketEast;

  constructor(protected ticketEastService: TicketEastService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ticketEastService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
