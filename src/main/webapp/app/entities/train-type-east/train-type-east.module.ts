import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TrainTypeEastComponent } from './list/train-type-east.component';
import { TrainTypeEastDetailComponent } from './detail/train-type-east-detail.component';
import { TrainTypeEastUpdateComponent } from './update/train-type-east-update.component';
import { TrainTypeEastDeleteDialogComponent } from './delete/train-type-east-delete-dialog.component';
import { TrainTypeEastRoutingModule } from './route/train-type-east-routing.module';

@NgModule({
  imports: [SharedModule, TrainTypeEastRoutingModule],
  declarations: [TrainTypeEastComponent, TrainTypeEastDetailComponent, TrainTypeEastUpdateComponent, TrainTypeEastDeleteDialogComponent],
})
export class TrainTypeEastModule {}
