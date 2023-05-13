import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAddressEast } from '../address-east.model';

@Component({
  selector: 'jhi-address-east-detail',
  templateUrl: './address-east-detail.component.html',
})
export class AddressEastDetailComponent implements OnInit {
  addressEast: IAddressEast | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ addressEast }) => {
      this.addressEast = addressEast;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
