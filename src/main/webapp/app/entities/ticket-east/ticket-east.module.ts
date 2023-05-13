import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TicketEastComponent } from './list/ticket-east.component';
import { TicketEastDetailComponent } from './detail/ticket-east-detail.component';
import { TicketEastUpdateComponent } from './update/ticket-east-update.component';
import { TicketEastDeleteDialogComponent } from './delete/ticket-east-delete-dialog.component';
import { TicketEastRoutingModule } from './route/ticket-east-routing.module';

@NgModule({
  imports: [SharedModule, TicketEastRoutingModule],
  declarations: [TicketEastComponent, TicketEastDetailComponent, TicketEastUpdateComponent, TicketEastDeleteDialogComponent],
})
export class TicketEastModule {}
