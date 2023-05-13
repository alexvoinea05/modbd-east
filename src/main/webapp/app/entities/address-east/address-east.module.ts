import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AddressEastComponent } from './list/address-east.component';
import { AddressEastDetailComponent } from './detail/address-east-detail.component';
import { AddressEastUpdateComponent } from './update/address-east-update.component';
import { AddressEastDeleteDialogComponent } from './delete/address-east-delete-dialog.component';
import { AddressEastRoutingModule } from './route/address-east-routing.module';

@NgModule({
  imports: [SharedModule, AddressEastRoutingModule],
  declarations: [AddressEastComponent, AddressEastDetailComponent, AddressEastUpdateComponent, AddressEastDeleteDialogComponent],
})
export class AddressEastModule {}
