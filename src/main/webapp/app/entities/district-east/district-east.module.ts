import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DistrictEastComponent } from './list/district-east.component';
import { DistrictEastDetailComponent } from './detail/district-east-detail.component';
import { DistrictEastUpdateComponent } from './update/district-east-update.component';
import { DistrictEastDeleteDialogComponent } from './delete/district-east-delete-dialog.component';
import { DistrictEastRoutingModule } from './route/district-east-routing.module';

@NgModule({
  imports: [SharedModule, DistrictEastRoutingModule],
  declarations: [DistrictEastComponent, DistrictEastDetailComponent, DistrictEastUpdateComponent, DistrictEastDeleteDialogComponent],
})
export class DistrictEastModule {}
