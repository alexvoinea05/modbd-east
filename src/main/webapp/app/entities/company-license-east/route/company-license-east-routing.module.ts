import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CompanyLicenseEastComponent } from '../list/company-license-east.component';
import { CompanyLicenseEastDetailComponent } from '../detail/company-license-east-detail.component';
import { CompanyLicenseEastUpdateComponent } from '../update/company-license-east-update.component';
import { CompanyLicenseEastRoutingResolveService } from './company-license-east-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const companyLicenseEastRoute: Routes = [
  {
    path: '',
    component: CompanyLicenseEastComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompanyLicenseEastDetailComponent,
    resolve: {
      companyLicenseEast: CompanyLicenseEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompanyLicenseEastUpdateComponent,
    resolve: {
      companyLicenseEast: CompanyLicenseEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompanyLicenseEastUpdateComponent,
    resolve: {
      companyLicenseEast: CompanyLicenseEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(companyLicenseEastRoute)],
  exports: [RouterModule],
})
export class CompanyLicenseEastRoutingModule {}
