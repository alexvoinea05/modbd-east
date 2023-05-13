import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRailwayStationEast, NewRailwayStationEast } from '../railway-station-east.model';

export type PartialUpdateRailwayStationEast = Partial<IRailwayStationEast> & Pick<IRailwayStationEast, 'id'>;

export type EntityResponseType = HttpResponse<IRailwayStationEast>;
export type EntityArrayResponseType = HttpResponse<IRailwayStationEast[]>;

@Injectable({ providedIn: 'root' })
export class RailwayStationEastService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/railway-station-easts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(railwayStationEast: NewRailwayStationEast): Observable<EntityResponseType> {
    return this.http.post<IRailwayStationEast>(this.resourceUrl, railwayStationEast, { observe: 'response' });
  }

  update(railwayStationEast: IRailwayStationEast): Observable<EntityResponseType> {
    return this.http.put<IRailwayStationEast>(
      `${this.resourceUrl}/${this.getRailwayStationEastIdentifier(railwayStationEast)}`,
      railwayStationEast,
      { observe: 'response' }
    );
  }

  partialUpdate(railwayStationEast: PartialUpdateRailwayStationEast): Observable<EntityResponseType> {
    return this.http.patch<IRailwayStationEast>(
      `${this.resourceUrl}/${this.getRailwayStationEastIdentifier(railwayStationEast)}`,
      railwayStationEast,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRailwayStationEast>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRailwayStationEast[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRailwayStationEastIdentifier(railwayStationEast: Pick<IRailwayStationEast, 'id'>): number {
    return railwayStationEast.id;
  }

  compareRailwayStationEast(o1: Pick<IRailwayStationEast, 'id'> | null, o2: Pick<IRailwayStationEast, 'id'> | null): boolean {
    return o1 && o2 ? this.getRailwayStationEastIdentifier(o1) === this.getRailwayStationEastIdentifier(o2) : o1 === o2;
  }

  addRailwayStationEastToCollectionIfMissing<Type extends Pick<IRailwayStationEast, 'id'>>(
    railwayStationEastCollection: Type[],
    ...railwayStationEastsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const railwayStationEasts: Type[] = railwayStationEastsToCheck.filter(isPresent);
    if (railwayStationEasts.length > 0) {
      const railwayStationEastCollectionIdentifiers = railwayStationEastCollection.map(
        railwayStationEastItem => this.getRailwayStationEastIdentifier(railwayStationEastItem)!
      );
      const railwayStationEastsToAdd = railwayStationEasts.filter(railwayStationEastItem => {
        const railwayStationEastIdentifier = this.getRailwayStationEastIdentifier(railwayStationEastItem);
        if (railwayStationEastCollectionIdentifiers.includes(railwayStationEastIdentifier)) {
          return false;
        }
        railwayStationEastCollectionIdentifiers.push(railwayStationEastIdentifier);
        return true;
      });
      return [...railwayStationEastsToAdd, ...railwayStationEastCollection];
    }
    return railwayStationEastCollection;
  }
}
