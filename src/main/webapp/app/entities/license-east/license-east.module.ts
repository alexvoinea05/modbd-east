import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LicenseEastComponent } from './list/license-east.component';
import { LicenseEastDetailComponent } from './detail/license-east-detail.component';
import { LicenseEastUpdateComponent } from './update/license-east-update.component';
import { LicenseEastDeleteDialogComponent } from './delete/license-east-delete-dialog.component';
import { LicenseEastRoutingModule } from './route/license-east-routing.module';

@NgModule({
  imports: [SharedModule, LicenseEastRoutingModule],
  declarations: [LicenseEastComponent, LicenseEastDetailComponent, LicenseEastUpdateComponent, LicenseEastDeleteDialogComponent],
})
export class LicenseEastModule {}
