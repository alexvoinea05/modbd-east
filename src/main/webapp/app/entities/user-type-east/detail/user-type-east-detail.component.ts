import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserTypeEast } from '../user-type-east.model';

@Component({
  selector: 'jhi-user-type-east-detail',
  templateUrl: './user-type-east-detail.component.html',
})
export class UserTypeEastDetailComponent implements OnInit {
  userTypeEast: IUserTypeEast | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userTypeEast }) => {
      this.userTypeEast = userTypeEast;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
