import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CompanyEastComponent } from '../list/company-east.component';
import { CompanyEastDetailComponent } from '../detail/company-east-detail.component';
import { CompanyEastUpdateComponent } from '../update/company-east-update.component';
import { CompanyEastRoutingResolveService } from './company-east-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const companyEastRoute: Routes = [
  {
    path: '',
    component: CompanyEastComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompanyEastDetailComponent,
    resolve: {
      companyEast: CompanyEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompanyEastUpdateComponent,
    resolve: {
      companyEast: CompanyEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompanyEastUpdateComponent,
    resolve: {
      companyEast: CompanyEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(companyEastRoute)],
  exports: [RouterModule],
})
export class CompanyEastRoutingModule {}
