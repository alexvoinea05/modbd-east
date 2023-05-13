import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RailwayTypeEastComponent } from '../list/railway-type-east.component';
import { RailwayTypeEastDetailComponent } from '../detail/railway-type-east-detail.component';
import { RailwayTypeEastUpdateComponent } from '../update/railway-type-east-update.component';
import { RailwayTypeEastRoutingResolveService } from './railway-type-east-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const railwayTypeEastRoute: Routes = [
  {
    path: '',
    component: RailwayTypeEastComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RailwayTypeEastDetailComponent,
    resolve: {
      railwayTypeEast: RailwayTypeEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RailwayTypeEastUpdateComponent,
    resolve: {
      railwayTypeEast: RailwayTypeEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RailwayTypeEastUpdateComponent,
    resolve: {
      railwayTypeEast: RailwayTypeEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(railwayTypeEastRoute)],
  exports: [RouterModule],
})
export class RailwayTypeEastRoutingModule {}
