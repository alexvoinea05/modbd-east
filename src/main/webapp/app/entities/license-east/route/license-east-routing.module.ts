import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LicenseEastComponent } from '../list/license-east.component';
import { LicenseEastDetailComponent } from '../detail/license-east-detail.component';
import { LicenseEastUpdateComponent } from '../update/license-east-update.component';
import { LicenseEastRoutingResolveService } from './license-east-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const licenseEastRoute: Routes = [
  {
    path: '',
    component: LicenseEastComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LicenseEastDetailComponent,
    resolve: {
      licenseEast: LicenseEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LicenseEastUpdateComponent,
    resolve: {
      licenseEast: LicenseEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LicenseEastUpdateComponent,
    resolve: {
      licenseEast: LicenseEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(licenseEastRoute)],
  exports: [RouterModule],
})
export class LicenseEastRoutingModule {}
