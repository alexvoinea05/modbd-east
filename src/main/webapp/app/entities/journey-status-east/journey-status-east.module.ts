import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { JourneyStatusEastComponent } from './list/journey-status-east.component';
import { JourneyStatusEastDetailComponent } from './detail/journey-status-east-detail.component';
import { JourneyStatusEastUpdateComponent } from './update/journey-status-east-update.component';
import { JourneyStatusEastDeleteDialogComponent } from './delete/journey-status-east-delete-dialog.component';
import { JourneyStatusEastRoutingModule } from './route/journey-status-east-routing.module';

@NgModule({
  imports: [SharedModule, JourneyStatusEastRoutingModule],
  declarations: [
    JourneyStatusEastComponent,
    JourneyStatusEastDetailComponent,
    JourneyStatusEastUpdateComponent,
    JourneyStatusEastDeleteDialogComponent,
  ],
})
export class JourneyStatusEastModule {}
