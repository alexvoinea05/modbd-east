import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IJourneyStatusEast } from '../journey-status-east.model';
import { JourneyStatusEastService } from '../service/journey-status-east.service';

@Injectable({ providedIn: 'root' })
export class JourneyStatusEastRoutingResolveService implements Resolve<IJourneyStatusEast | null> {
  constructor(protected service: JourneyStatusEastService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJourneyStatusEast | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((journeyStatusEast: HttpResponse<IJourneyStatusEast>) => {
          if (journeyStatusEast.body) {
            return of(journeyStatusEast.body);
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
