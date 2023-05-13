import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CompanyEastComponent } from './list/company-east.component';
import { CompanyEastDetailComponent } from './detail/company-east-detail.component';
import { CompanyEastUpdateComponent } from './update/company-east-update.component';
import { CompanyEastDeleteDialogComponent } from './delete/company-east-delete-dialog.component';
import { CompanyEastRoutingModule } from './route/company-east-routing.module';

@NgModule({
  imports: [SharedModule, CompanyEastRoutingModule],
  declarations: [CompanyEastComponent, CompanyEastDetailComponent, CompanyEastUpdateComponent, CompanyEastDeleteDialogComponent],
})
export class CompanyEastModule {}
