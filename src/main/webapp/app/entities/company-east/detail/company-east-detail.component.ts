import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanyEast } from '../company-east.model';

@Component({
  selector: 'jhi-company-east-detail',
  templateUrl: './company-east-detail.component.html',
})
export class CompanyEastDetailComponent implements OnInit {
  companyEast: ICompanyEast | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyEast }) => {
      this.companyEast = companyEast;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
