import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICompanyLicenseEast } from '../company-license-east.model';
import { CompanyLicenseEastService } from '../service/company-license-east.service';

@Injectable({ providedIn: 'root' })
export class CompanyLicenseEastRoutingResolveService implements Resolve<ICompanyLicenseEast | null> {
  constructor(protected service: CompanyLicenseEastService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompanyLicenseEast | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((companyLicenseEast: HttpResponse<ICompanyLicenseEast>) => {
          if (companyLicenseEast.body) {
            return of(companyLicenseEast.body);
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
