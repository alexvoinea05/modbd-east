import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITrainTypeEast } from '../train-type-east.model';
import { TrainTypeEastService } from '../service/train-type-east.service';

@Injectable({ providedIn: 'root' })
export class TrainTypeEastRoutingResolveService implements Resolve<ITrainTypeEast | null> {
  constructor(protected service: TrainTypeEastService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrainTypeEast | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((trainTypeEast: HttpResponse<ITrainTypeEast>) => {
          if (trainTypeEast.body) {
            return of(trainTypeEast.body);
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
