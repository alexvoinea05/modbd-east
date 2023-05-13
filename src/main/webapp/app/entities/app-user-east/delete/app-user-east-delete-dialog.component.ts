import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAppUserEast } from '../app-user-east.model';
import { AppUserEastService } from '../service/app-user-east.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './app-user-east-delete-dialog.component.html',
})
export class AppUserEastDeleteDialogComponent {
  appUserEast?: IAppUserEast;

  constructor(protected appUserEastService: AppUserEastService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.appUserEastService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
