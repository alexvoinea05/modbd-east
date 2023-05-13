import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDistrictEast } from '../district-east.model';
import { DistrictEastService } from '../service/district-east.service';

@Injectable({ providedIn: 'root' })
export class DistrictEastRoutingResolveService implements Resolve<IDistrictEast | null> {
  constructor(protected service: DistrictEastService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDistrictEast | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((districtEast: HttpResponse<IDistrictEast>) => {
          if (districtEast.body) {
            return of(districtEast.body);
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
