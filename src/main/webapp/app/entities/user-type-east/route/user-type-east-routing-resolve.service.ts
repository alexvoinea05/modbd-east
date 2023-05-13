import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserTypeEast } from '../user-type-east.model';
import { UserTypeEastService } from '../service/user-type-east.service';

@Injectable({ providedIn: 'root' })
export class UserTypeEastRoutingResolveService implements Resolve<IUserTypeEast | null> {
  constructor(protected service: UserTypeEastService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserTypeEast | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userTypeEast: HttpResponse<IUserTypeEast>) => {
          if (userTypeEast.body) {
            return of(userTypeEast.body);
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
