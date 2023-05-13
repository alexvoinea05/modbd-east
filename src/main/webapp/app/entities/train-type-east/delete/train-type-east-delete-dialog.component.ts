import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrainTypeEast } from '../train-type-east.model';
import { TrainTypeEastService } from '../service/train-type-east.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './train-type-east-delete-dialog.component.html',
})
export class TrainTypeEastDeleteDialogComponent {
  trainTypeEast?: ITrainTypeEast;

  constructor(protected trainTypeEastService: TrainTypeEastService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trainTypeEastService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
