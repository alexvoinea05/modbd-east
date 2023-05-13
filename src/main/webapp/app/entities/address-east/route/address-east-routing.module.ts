import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AddressEastComponent } from '../list/address-east.component';
import { AddressEastDetailComponent } from '../detail/address-east-detail.component';
import { AddressEastUpdateComponent } from '../update/address-east-update.component';
import { AddressEastRoutingResolveService } from './address-east-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const addressEastRoute: Routes = [
  {
    path: '',
    component: AddressEastComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AddressEastDetailComponent,
    resolve: {
      addressEast: AddressEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AddressEastUpdateComponent,
    resolve: {
      addressEast: AddressEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AddressEastUpdateComponent,
    resolve: {
      addressEast: AddressEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(addressEastRoute)],
  exports: [RouterModule],
})
export class AddressEastRoutingModule {}
