import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILicenseEast } from '../license-east.model';

@Component({
  selector: 'jhi-license-east-detail',
  templateUrl: './license-east-detail.component.html',
})
export class LicenseEastDetailComponent implements OnInit {
  licenseEast: ILicenseEast | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ licenseEast }) => {
      this.licenseEast = licenseEast;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
