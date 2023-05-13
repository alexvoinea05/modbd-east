import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAddressEast } from '../address-east.model';
import { AddressEastService } from '../service/address-east.service';

@Injectable({ providedIn: 'root' })
export class AddressEastRoutingResolveService implements Resolve<IAddressEast | null> {
  constructor(protected service: AddressEastService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAddressEast | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((addressEast: HttpResponse<IAddressEast>) => {
          if (addressEast.body) {
            return of(addressEast.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
