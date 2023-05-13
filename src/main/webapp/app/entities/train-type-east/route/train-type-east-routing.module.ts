import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TrainTypeEastComponent } from '../list/train-type-east.component';
import { TrainTypeEastDetailComponent } from '../detail/train-type-east-detail.component';
import { TrainTypeEastUpdateComponent } from '../update/train-type-east-update.component';
import { TrainTypeEastRoutingResolveService } from './train-type-east-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const trainTypeEastRoute: Routes = [
  {
    path: '',
    component: TrainTypeEastComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrainTypeEastDetailComponent,
    resolve: {
      trainTypeEast: TrainTypeEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrainTypeEastUpdateComponent,
    resolve: {
      trainTypeEast: TrainTypeEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrainTypeEastUpdateComponent,
    resolve: {
      trainTypeEast: TrainTypeEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(trainTypeEastRoute)],
  exports: [RouterModule],
})
export class TrainTypeEastRoutingModule {}
