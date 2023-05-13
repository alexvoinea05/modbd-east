import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RailwayStationEastComponent } from '../list/railway-station-east.component';
import { RailwayStationEastDetailComponent } from '../detail/railway-station-east-detail.component';
import { RailwayStationEastUpdateComponent } from '../update/railway-station-east-update.component';
import { RailwayStationEastRoutingResolveService } from './railway-station-east-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const railwayStationEastRoute: Routes = [
  {
    path: '',
    component: RailwayStationEastComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RailwayStationEastDetailComponent,
    resolve: {
      railwayStationEast: RailwayStationEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RailwayStationEastUpdateComponent,
    resolve: {
      railwayStationEast: RailwayStationEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RailwayStationEastUpdateComponent,
    resolve: {
      railwayStationEast: RailwayStationEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(railwayStationEastRoute)],
  exports: [RouterModule],
})
export class RailwayStationEastRoutingModule {}
