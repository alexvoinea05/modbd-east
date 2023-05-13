import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FuelTypeEastComponent } from './list/fuel-type-east.component';
import { FuelTypeEastDetailComponent } from './detail/fuel-type-east-detail.component';
import { FuelTypeEastUpdateComponent } from './update/fuel-type-east-update.component';
import { FuelTypeEastDeleteDialogComponent } from './delete/fuel-type-east-delete-dialog.component';
import { FuelTypeEastRoutingModule } from './route/fuel-type-east-routing.module';

@NgModule({
  imports: [SharedModule, FuelTypeEastRoutingModule],
  declarations: [FuelTypeEastComponent, FuelTypeEastDetailComponent, FuelTypeEastUpdateComponent, FuelTypeEastDeleteDialogComponent],
})
export class FuelTypeEastModule {}
