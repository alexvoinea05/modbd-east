import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { JourneyStatusEastComponent } from '../list/journey-status-east.component';
import { JourneyStatusEastDetailComponent } from '../detail/journey-status-east-detail.component';
import { JourneyStatusEastUpdateComponent } from '../update/journey-status-east-update.component';
import { JourneyStatusEastRoutingResolveService } from './journey-status-east-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const journeyStatusEastRoute: Routes = [
  {
    path: '',
    component: JourneyStatusEastComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JourneyStatusEastDetailComponent,
    resolve: {
      journeyStatusEast: JourneyStatusEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JourneyStatusEastUpdateComponent,
    resolve: {
      journeyStatusEast: JourneyStatusEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JourneyStatusEastUpdateComponent,
    resolve: {
      journeyStatusEast: JourneyStatusEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(journeyStatusEastRoute)],
  exports: [RouterModule],
})
export class JourneyStatusEastRoutingModule {}
