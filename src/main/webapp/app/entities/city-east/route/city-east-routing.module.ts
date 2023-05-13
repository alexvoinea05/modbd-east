import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CityEastComponent } from '../list/city-east.component';
import { CityEastDetailComponent } from '../detail/city-east-detail.component';
import { CityEastUpdateComponent } from '../update/city-east-update.component';
import { CityEastRoutingResolveService } from './city-east-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const cityEastRoute: Routes = [
  {
    path: '',
    component: CityEastComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CityEastDetailComponent,
    resolve: {
      cityEast: CityEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CityEastUpdateComponent,
    resolve: {
      cityEast: CityEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CityEastUpdateComponent,
    resolve: {
      cityEast: CityEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cityEastRoute)],
  exports: [RouterModule],
})
export class CityEastRoutingModule {}
