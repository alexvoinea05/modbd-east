import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DistrictEastComponent } from '../list/district-east.component';
import { DistrictEastDetailComponent } from '../detail/district-east-detail.component';
import { DistrictEastUpdateComponent } from '../update/district-east-update.component';
import { DistrictEastRoutingResolveService } from './district-east-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const districtEastRoute: Routes = [
  {
    path: '',
    component: DistrictEastComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DistrictEastDetailComponent,
    resolve: {
      districtEast: DistrictEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DistrictEastUpdateComponent,
    resolve: {
      districtEast: DistrictEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DistrictEastUpdateComponent,
    resolve: {
      districtEast: DistrictEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(districtEastRoute)],
  exports: [RouterModule],
})
export class DistrictEastRoutingModule {}
