import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrainEast } from '../train-east.model';
import { TrainEastService } from '../service/train-east.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './train-east-delete-dialog.component.html',
})
export class TrainEastDeleteDialogComponent {
  trainEast?: ITrainEast;

  constructor(protected trainEastService: TrainEastService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trainEastService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
