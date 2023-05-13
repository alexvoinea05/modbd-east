import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICityEast } from '../city-east.model';
import { CityEastService } from '../service/city-east.service';

@Injectable({ providedIn: 'root' })
export class CityEastRoutingResolveService implements Resolve<ICityEast | null> {
  constructor(protected service: CityEastService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICityEast | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cityEast: HttpResponse<ICityEast>) => {
          if (cityEast.body) {
            return of(cityEast.body);
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
