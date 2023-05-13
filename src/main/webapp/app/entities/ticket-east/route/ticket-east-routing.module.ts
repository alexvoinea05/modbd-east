import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TicketEastComponent } from '../list/ticket-east.component';
import { TicketEastDetailComponent } from '../detail/ticket-east-detail.component';
import { TicketEastUpdateComponent } from '../update/ticket-east-update.component';
import { TicketEastRoutingResolveService } from './ticket-east-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const ticketEastRoute: Routes = [
  {
    path: '',
    component: TicketEastComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TicketEastDetailComponent,
    resolve: {
      ticketEast: TicketEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TicketEastUpdateComponent,
    resolve: {
      ticketEast: TicketEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TicketEastUpdateComponent,
    resolve: {
      ticketEast: TicketEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ticketEastRoute)],
  exports: [RouterModule],
})
export class TicketEastRoutingModule {}
