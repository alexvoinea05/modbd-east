import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAppUserEast } from '../app-user-east.model';
import { AppUserEastService } from '../service/app-user-east.service';

@Injectable({ providedIn: 'root' })
export class AppUserEastRoutingResolveService implements Resolve<IAppUserEast | null> {
  constructor(protected service: AppUserEastService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAppUserEast | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((appUserEast: HttpResponse<IAppUserEast>) => {
          if (appUserEast.body) {
            return of(appUserEast.body);
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
