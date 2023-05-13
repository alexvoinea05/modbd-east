import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserTypeEastComponent } from '../list/user-type-east.component';
import { UserTypeEastDetailComponent } from '../detail/user-type-east-detail.component';
import { UserTypeEastUpdateComponent } from '../update/user-type-east-update.component';
import { UserTypeEastRoutingResolveService } from './user-type-east-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const userTypeEastRoute: Routes = [
  {
    path: '',
    component: UserTypeEastComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserTypeEastDetailComponent,
    resolve: {
      userTypeEast: UserTypeEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserTypeEastUpdateComponent,
    resolve: {
      userTypeEast: UserTypeEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserTypeEastUpdateComponent,
    resolve: {
      userTypeEast: UserTypeEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userTypeEastRoute)],
  exports: [RouterModule],
})
export class UserTypeEastRoutingModule {}
