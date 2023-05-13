import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserTypeEastComponent } from './list/user-type-east.component';
import { UserTypeEastDetailComponent } from './detail/user-type-east-detail.component';
import { UserTypeEastUpdateComponent } from './update/user-type-east-update.component';
import { UserTypeEastDeleteDialogComponent } from './delete/user-type-east-delete-dialog.component';
import { UserTypeEastRoutingModule } from './route/user-type-east-routing.module';

@NgModule({
  imports: [SharedModule, UserTypeEastRoutingModule],
  declarations: [UserTypeEastComponent, UserTypeEastDetailComponent, UserTypeEastUpdateComponent, UserTypeEastDeleteDialogComponent],
})
export class UserTypeEastModule {}
