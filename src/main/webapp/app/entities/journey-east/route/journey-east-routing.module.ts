import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { JourneyEastComponent } from '../list/journey-east.component';
import { JourneyEastDetailComponent } from '../detail/journey-east-detail.component';
import { JourneyEastUpdateComponent } from '../update/journey-east-update.component';
import { JourneyEastRoutingResolveService } from './journey-east-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const journeyEastRoute: Routes = [
  {
    path: '',
    component: JourneyEastComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JourneyEastDetailComponent,
    resolve: {
      journeyEast: JourneyEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JourneyEastUpdateComponent,
    resolve: {
      journeyEast: JourneyEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JourneyEastUpdateComponent,
    resolve: {
      journeyEast: JourneyEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(journeyEastRoute)],
  exports: [RouterModule],
})
export class JourneyEastRoutingModule {}
