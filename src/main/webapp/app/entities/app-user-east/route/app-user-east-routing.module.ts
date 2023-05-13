import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AppUserEastComponent } from '../list/app-user-east.component';
import { AppUserEastDetailComponent } from '../detail/app-user-east-detail.component';
import { AppUserEastUpdateComponent } from '../update/app-user-east-update.component';
import { AppUserEastRoutingResolveService } from './app-user-east-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const appUserEastRoute: Routes = [
  {
    path: '',
    component: AppUserEastComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AppUserEastDetailComponent,
    resolve: {
      appUserEast: AppUserEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AppUserEastUpdateComponent,
    resolve: {
      appUserEast: AppUserEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AppUserEastUpdateComponent,
    resolve: {
      appUserEast: AppUserEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(appUserEastRoute)],
  exports: [RouterModule],
})
export class AppUserEastRoutingModule {}
