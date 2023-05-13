import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RailwayStationEastComponent } from './list/railway-station-east.component';
import { RailwayStationEastDetailComponent } from './detail/railway-station-east-detail.component';
import { RailwayStationEastUpdateComponent } from './update/railway-station-east-update.component';
import { RailwayStationEastDeleteDialogComponent } from './delete/railway-station-east-delete-dialog.component';
import { RailwayStationEastRoutingModule } from './route/railway-station-east-routing.module';

@NgModule({
  imports: [SharedModule, RailwayStationEastRoutingModule],
  declarations: [
    RailwayStationEastComponent,
    RailwayStationEastDetailComponent,
    RailwayStationEastUpdateComponent,
    RailwayStationEastDeleteDialogComponent,
  ],
})
export class RailwayStationEastModule {}
