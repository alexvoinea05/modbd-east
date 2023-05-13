import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICompanyEast } from '../company-east.model';
import { CompanyEastService } from '../service/company-east.service';

@Injectable({ providedIn: 'root' })
export class CompanyEastRoutingResolveService implements Resolve<ICompanyEast | null> {
  constructor(protected service: CompanyEastService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompanyEast | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((companyEast: HttpResponse<ICompanyEast>) => {
          if (companyEast.body) {
            return of(companyEast.body);
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
