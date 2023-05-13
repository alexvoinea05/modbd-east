import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITicketEast } from '../ticket-east.model';
import { TicketEastService } from '../service/ticket-east.service';

@Injectable({ providedIn: 'root' })
export class TicketEastRoutingResolveService implements Resolve<ITicketEast | null> {
  constructor(protected service: TicketEastService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITicketEast | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ticketEast: HttpResponse<ITicketEast>) => {
          if (ticketEast.body) {
            return of(ticketEast.body);
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
