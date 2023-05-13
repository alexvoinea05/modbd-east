import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AppUserEastComponent } from './list/app-user-east.component';
import { AppUserEastDetailComponent } from './detail/app-user-east-detail.component';
import { AppUserEastUpdateComponent } from './update/app-user-east-update.component';
import { AppUserEastDeleteDialogComponent } from './delete/app-user-east-delete-dialog.component';
import { AppUserEastRoutingModule } from './route/app-user-east-routing.module';

@NgModule({
  imports: [SharedModule, AppUserEastRoutingModule],
  declarations: [AppUserEastComponent, AppUserEastDetailComponent, AppUserEastUpdateComponent, AppUserEastDeleteDialogComponent],
})
export class AppUserEastModule {}
