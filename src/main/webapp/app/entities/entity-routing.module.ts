import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'address-east',
        data: { pageTitle: 'AddressEasts' },
        loadChildren: () => import('./address-east/address-east.module').then(m => m.AddressEastModule),
      },
      {
        path: 'app-user-east',
        data: { pageTitle: 'AppUserEasts' },
        loadChildren: () => import('./app-user-east/app-user-east.module').then(m => m.AppUserEastModule),
      },
      {
        path: 'city-east',
        data: { pageTitle: 'CityEasts' },
        loadChildren: () => import('./city-east/city-east.module').then(m => m.CityEastModule),
      },
      {
        path: 'company-east',
        data: { pageTitle: 'CompanyEasts' },
        loadChildren: () => import('./company-east/company-east.module').then(m => m.CompanyEastModule),
      },
      {
        path: 'company-license-east',
        data: { pageTitle: 'CompanyLicenseEasts' },
        loadChildren: () => import('./company-license-east/company-license-east.module').then(m => m.CompanyLicenseEastModule),
      },
      {
        path: 'district-east',
        data: { pageTitle: 'DistrictEasts' },
        loadChildren: () => import('./district-east/district-east.module').then(m => m.DistrictEastModule),
      },
      {
        path: 'fuel-type-east',
        data: { pageTitle: 'FuelTypeEasts' },
        loadChildren: () => import('./fuel-type-east/fuel-type-east.module').then(m => m.FuelTypeEastModule),
      },
      {
        path: 'journey-east',
        data: { pageTitle: 'JourneyEasts' },
        loadChildren: () => import('./journey-east/journey-east.module').then(m => m.JourneyEastModule),
      },
      {
        path: 'journey-status-east',
        data: { pageTitle: 'JourneyStatusEasts' },
        loadChildren: () => import('./journey-status-east/journey-status-east.module').then(m => m.JourneyStatusEastModule),
      },
      {
        path: 'license-east',
        data: { pageTitle: 'LicenseEasts' },
        loadChildren: () => import('./license-east/license-east.module').then(m => m.LicenseEastModule),
      },
      {
        path: 'railway-station-east',
        data: { pageTitle: 'RailwayStationEasts' },
        loadChildren: () => import('./railway-station-east/railway-station-east.module').then(m => m.RailwayStationEastModule),
      },
      {
        path: 'railway-type-east',
        data: { pageTitle: 'RailwayTypeEasts' },
        loadChildren: () => import('./railway-type-east/railway-type-east.module').then(m => m.RailwayTypeEastModule),
      },
      {
        path: 'ticket-east',
        data: { pageTitle: 'TicketEasts' },
        loadChildren: () => import('./ticket-east/ticket-east.module').then(m => m.TicketEastModule),
      },
      {
        path: 'train-east',
        data: { pageTitle: 'TrainEasts' },
        loadChildren: () => import('./train-east/train-east.module').then(m => m.TrainEastModule),
      },
      {
        path: 'train-type-east',
        data: { pageTitle: 'TrainTypeEasts' },
        loadChildren: () => import('./train-type-east/train-type-east.module').then(m => m.TrainTypeEastModule),
      },
      {
        path: 'user-type-east',
        data: { pageTitle: 'UserTypeEasts' },
        loadChildren: () => import('./user-type-east/user-type-east.module').then(m => m.UserTypeEastModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
