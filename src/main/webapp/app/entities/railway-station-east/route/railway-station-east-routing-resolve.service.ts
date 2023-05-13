import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRailwayStationEast } from '../railway-station-east.model';
import { RailwayStationEastService } from '../service/railway-station-east.service';

@Injectable({ providedIn: 'root' })
export class RailwayStationEastRoutingResolveService implements Resolve<IRailwayStationEast | null> {
  constructor(protected service: RailwayStationEastService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRailwayStationEast | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((railwayStationEast: HttpResponse<IRailwayStationEast>) => {
          if (railwayStationEast.body) {
            return of(railwayStationEast.body);
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
