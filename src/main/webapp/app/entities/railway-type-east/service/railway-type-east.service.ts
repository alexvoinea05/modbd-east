import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRailwayTypeEast, NewRailwayTypeEast } from '../railway-type-east.model';

export type PartialUpdateRailwayTypeEast = Partial<IRailwayTypeEast> & Pick<IRailwayTypeEast, 'id'>;

export type EntityResponseType = HttpResponse<IRailwayTypeEast>;
export type EntityArrayResponseType = HttpResponse<IRailwayTypeEast[]>;

@Injectable({ providedIn: 'root' })
export class RailwayTypeEastService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/railway-type-easts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(railwayTypeEast: NewRailwayTypeEast): Observable<EntityResponseType> {
    return this.http.post<IRailwayTypeEast>(this.resourceUrl, railwayTypeEast, { observe: 'response' });
  }

  update(railwayTypeEast: IRailwayTypeEast): Observable<EntityResponseType> {
    return this.http.put<IRailwayTypeEast>(`${this.resourceUrl}/${this.getRailwayTypeEastIdentifier(railwayTypeEast)}`, railwayTypeEast, {
      observe: 'response',
    });
  }

  partialUpdate(railwayTypeEast: PartialUpdateRailwayTypeEast): Observable<EntityResponseType> {
    return this.http.patch<IRailwayTypeEast>(`${this.resourceUrl}/${this.getRailwayTypeEastIdentifier(railwayTypeEast)}`, railwayTypeEast, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRailwayTypeEast>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRailwayTypeEast[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRailwayTypeEastIdentifier(railwayTypeEast: Pick<IRailwayTypeEast, 'id'>): number {
    return railwayTypeEast.id;
  }

  compareRailwayTypeEast(o1: Pick<IRailwayTypeEast, 'id'> | null, o2: Pick<IRailwayTypeEast, 'id'> | null): boolean {
    return o1 && o2 ? this.getRailwayTypeEastIdentifier(o1) === this.getRailwayTypeEastIdentifier(o2) : o1 === o2;
  }

  addRailwayTypeEastToCollectionIfMissing<Type extends Pick<IRailwayTypeEast, 'id'>>(
    railwayTypeEastCollection: Type[],
    ...railwayTypeEastsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const railwayTypeEasts: Type[] = railwayTypeEastsToCheck.filter(isPresent);
    if (railwayTypeEasts.length > 0) {
      const railwayTypeEastCollectionIdentifiers = railwayTypeEastCollection.map(
        railwayTypeEastItem => this.getRailwayTypeEastIdentifier(railwayTypeEastItem)!
      );
      const railwayTypeEastsToAdd = railwayTypeEasts.filter(railwayTypeEastItem => {
        const railwayTypeEastIdentifier = this.getRailwayTypeEastIdentifier(railwayTypeEastItem);
        if (railwayTypeEastCollectionIdentifiers.includes(railwayTypeEastIdentifier)) {
          return false;
        }
        railwayTypeEastCollectionIdentifiers.push(railwayTypeEastIdentifier);
        return true;
      });
      return [...railwayTypeEastsToAdd, ...railwayTypeEastCollection];
    }
    return railwayTypeEastCollection;
  }
}
