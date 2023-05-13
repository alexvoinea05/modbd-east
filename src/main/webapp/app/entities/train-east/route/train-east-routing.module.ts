import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TrainEastComponent } from '../list/train-east.component';
import { TrainEastDetailComponent } from '../detail/train-east-detail.component';
import { TrainEastUpdateComponent } from '../update/train-east-update.component';
import { TrainEastRoutingResolveService } from './train-east-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const trainEastRoute: Routes = [
  {
    path: '',
    component: TrainEastComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrainEastDetailComponent,
    resolve: {
      trainEast: TrainEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrainEastUpdateComponent,
    resolve: {
      trainEast: TrainEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrainEastUpdateComponent,
    resolve: {
      trainEast: TrainEastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(trainEastRoute)],
  exports: [RouterModule],
})
export class TrainEastRoutingModule {}
