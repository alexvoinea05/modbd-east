import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserTypeEast } from '../user-type-east.model';
import { UserTypeEastService } from '../service/user-type-east.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './user-type-east-delete-dialog.component.html',
})
export class UserTypeEastDeleteDialogComponent {
  userTypeEast?: IUserTypeEast;

  constructor(protected userTypeEastService: UserTypeEastService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userTypeEastService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
