import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CityEastComponent } from './list/city-east.component';
import { CityEastDetailComponent } from './detail/city-east-detail.component';
import { CityEastUpdateComponent } from './update/city-east-update.component';
import { CityEastDeleteDialogComponent } from './delete/city-east-delete-dialog.component';
import { CityEastRoutingModule } from './route/city-east-routing.module';

@NgModule({
  imports: [SharedModule, CityEastRoutingModule],
  declarations: [CityEastComponent, CityEastDetailComponent, CityEastUpdateComponent, CityEastDeleteDialogComponent],
})
export class CityEastModule {}
