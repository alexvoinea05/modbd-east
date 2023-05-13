import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FuelTypeEastComponent } from '../list/fuel-type-east.component';
import { FuelTypeEastDetailComponent } from '../detail/fuel-type-east-detail.component';
import { FuelTypeEastUpdateComponent } from '../update/fuel-type-east-update.component';
import { FuelTypeEastRoutingResolveService } from './fuel-type-east-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fuelTypeEastRoute: Routes = [
  {
    path: '',
    component: FuelTypeEastComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FuelTypeEastDetailComponent,
    resolve: {
      fuelTypeEast: FuelTypeEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FuelTypeEastUpdateComponent,
    resolve: {
      fuelTypeEast: FuelTypeEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FuelTypeEastUpdateComponent,
    resolve: {
      fuelTypeEast: FuelTypeEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fuelTypeEastRoute)],
  exports: [RouterModule],
})
export class FuelTypeEastRoutingModule {}
