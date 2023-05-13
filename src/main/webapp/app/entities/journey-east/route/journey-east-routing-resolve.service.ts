import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IJourneyEast } from '../journey-east.model';
import { JourneyEastService } from '../service/journey-east.service';

@Injectable({ providedIn: 'root' })
export class JourneyEastRoutingResolveService implements Resolve<IJourneyEast | null> {
  constructor(protected service: JourneyEastService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJourneyEast | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((journeyEast: HttpResponse<IJourneyEast>) => {
          if (journeyEast.body) {
            return of(journeyEast.body);
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
