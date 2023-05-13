import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITrainEast } from '../train-east.model';
import { TrainEastService } from '../service/train-east.service';

@Injectable({ providedIn: 'root' })
export class TrainEastRoutingResolveService implements Resolve<ITrainEast | null> {
  constructor(protected service: TrainEastService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrainEast | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((trainEast: HttpResponse<ITrainEast>) => {
          if (trainEast.body) {
            return of(trainEast.body);
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
