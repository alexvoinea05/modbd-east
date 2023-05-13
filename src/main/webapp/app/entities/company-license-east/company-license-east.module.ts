import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CompanyLicenseEastComponent } from './list/company-license-east.component';
import { CompanyLicenseEastDetailComponent } from './detail/company-license-east-detail.component';
import { CompanyLicenseEastUpdateComponent } from './update/company-license-east-update.component';
import { CompanyLicenseEastDeleteDialogComponent } from './delete/company-license-east-delete-dialog.component';
import { CompanyLicenseEastRoutingModule } from './route/company-license-east-routing.module';

@NgModule({
  imports: [SharedModule, CompanyLicenseEastRoutingModule],
  declarations: [
    CompanyLicenseEastComponent,
    CompanyLicenseEastDetailComponent,
    CompanyLicenseEastUpdateComponent,
    CompanyLicenseEastDeleteDialogComponent,
  ],
})
export class CompanyLicenseEastModule {}
