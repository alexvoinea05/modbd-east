import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppUserEast } from '../app-user-east.model';

@Component({
  selector: 'jhi-app-user-east-detail',
  templateUrl: './app-user-east-detail.component.html',
})
export class AppUserEastDetailComponent implements OnInit {
  appUserEast: IAppUserEast | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appUserEast }) => {
      this.appUserEast = appUserEast;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
