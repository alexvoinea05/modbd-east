import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFuelTypeEast } from '../fuel-type-east.model';
import { FuelTypeEastService } from '../service/fuel-type-east.service';

@Injectable({ providedIn: 'root' })
export class FuelTypeEastRoutingResolveService implements Resolve<IFuelTypeEast | null> {
  constructor(protected service: FuelTypeEastService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFuelTypeEast | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fuelTypeEast: HttpResponse<IFuelTypeEast>) => {
          if (fuelTypeEast.body) {
            return of(fuelTypeEast.body);
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
