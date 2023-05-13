import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { JourneyEastComponent } from './list/journey-east.component';
import { JourneyEastDetailComponent } from './detail/journey-east-detail.component';
import { JourneyEastUpdateComponent } from './update/journey-east-update.component';
import { JourneyEastDeleteDialogComponent } from './delete/journey-east-delete-dialog.component';
import { JourneyEastRoutingModule } from './route/journey-east-routing.module';

@NgModule({
  imports: [SharedModule, JourneyEastRoutingModule],
  declarations: [JourneyEastComponent, JourneyEastDetailComponent, JourneyEastUpdateComponent, JourneyEastDeleteDialogComponent],
})
export class JourneyEastModule {}
