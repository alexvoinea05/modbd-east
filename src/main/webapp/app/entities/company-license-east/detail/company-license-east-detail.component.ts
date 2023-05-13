import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanyLicenseEast } from '../company-license-east.model';

@Component({
  selector: 'jhi-company-license-east-detail',
  templateUrl: './company-license-east-detail.component.html',
})
export class CompanyLicenseEastDetailComponent implements OnInit {
  companyLicenseEast: ICompanyLicenseEast | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyLicenseEast }) => {
      this.companyLicenseEast = companyLicenseEast;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
