import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILicenseEast } from '../license-east.model';
import { LicenseEastService } from '../service/license-east.service';

@Injectable({ providedIn: 'root' })
export class LicenseEastRoutingResolveService implements Resolve<ILicenseEast | null> {
  constructor(protected service: LicenseEastService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILicenseEast | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((licenseEast: HttpResponse<ILicenseEast>) => {
          if (licenseEast.body) {
            return of(licenseEast.body);
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
