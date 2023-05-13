import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TrainEastComponent } from './list/train-east.component';
import { TrainEastDetailComponent } from './detail/train-east-detail.component';
import { TrainEastUpdateComponent } from './update/train-east-update.component';
import { TrainEastDeleteDialogComponent } from './delete/train-east-delete-dialog.component';
import { TrainEastRoutingModule } from './route/train-east-routing.module';

@NgModule({
  imports: [SharedModule, TrainEastRoutingModule],
  declarations: [TrainEastComponent, TrainEastDetailComponent, TrainEastUpdateComponent, TrainEastDeleteDialogComponent],
})
export class TrainEastModule {}
