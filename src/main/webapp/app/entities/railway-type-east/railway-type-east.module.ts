import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RailwayTypeEastComponent } from './list/railway-type-east.component';
import { RailwayTypeEastDetailComponent } from './detail/railway-type-east-detail.component';
import { RailwayTypeEastUpdateComponent } from './update/railway-type-east-update.component';
import { RailwayTypeEastDeleteDialogComponent } from './delete/railway-type-east-delete-dialog.component';
import { RailwayTypeEastRoutingModule } from './route/railway-type-east-routing.module';

@NgModule({
  imports: [SharedModule, RailwayTypeEastRoutingModule],
  declarations: [
    RailwayTypeEastComponent,
    RailwayTypeEastDetailComponent,
    RailwayTypeEastUpdateComponent,
    RailwayTypeEastDeleteDialogComponent,
  ],
})
export class RailwayTypeEastModule {}
