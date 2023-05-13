import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRailwayTypeEast } from '../railway-type-east.model';
import { RailwayTypeEastService } from '../service/railway-type-east.service';

@Injectable({ providedIn: 'root' })
export class RailwayTypeEastRoutingResolveService implements Resolve<IRailwayTypeEast | null> {
  constructor(protected service: RailwayTypeEastService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRailwayTypeEast | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((railwayTypeEast: HttpResponse<IRailwayTypeEast>) => {
          if (railwayTypeEast.body) {
            return of(railwayTypeEast.body);
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
