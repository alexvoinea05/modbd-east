import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IJourneyEast, NewJourneyEast } from '../journey-east.model';

export type PartialUpdateJourneyEast = Partial<IJourneyEast> & Pick<IJourneyEast, 'id'>;

type RestOf<T extends IJourneyEast | NewJourneyEast> = Omit<
  T,
  'actualDepartureTime' | 'plannedDepartureTime' | 'actualArrivalTime' | 'plannedArrivalTime'
> & {
  actualDepartureTime?: string | null;
  plannedDepartureTime?: string | null;
  actualArrivalTime?: string | null;
  plannedArrivalTime?: string | null;
};

export type RestJourneyEast = RestOf<IJourneyEast>;

export type NewRestJourneyEast = RestOf<NewJourneyEast>;

export type PartialUpdateRestJourneyEast = RestOf<PartialUpdateJourneyEast>;

export type EntityResponseType = HttpResponse<IJourneyEast>;
export type EntityArrayResponseType = HttpResponse<IJourneyEast[]>;

@Injectable({ providedIn: 'root' })
export class JourneyEastService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/journey-easts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(journeyEast: NewJourneyEast): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(journeyEast);
    return this.http
      .post<RestJourneyEast>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(journeyEast: IJourneyEast): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(journeyEast);
    return this.http
      .put<RestJourneyEast>(`${this.resourceUrl}/${this.getJourneyEastIdentifier(journeyEast)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(journeyEast: PartialUpdateJourneyEast): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(journeyEast);
    return this.http
      .patch<RestJourneyEast>(`${this.resourceUrl}/${this.getJourneyEastIdentifier(journeyEast)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestJourneyEast>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestJourneyEast[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getJourneyEastIdentifier(journeyEast: Pick<IJourneyEast, 'id'>): number {
    return journeyEast.id;
  }

  compareJourneyEast(o1: Pick<IJourneyEast, 'id'> | null, o2: Pick<IJourneyEast, 'id'> | null): boolean {
    return o1 && o2 ? this.getJourneyEastIdentifier(o1) === this.getJourneyEastIdentifier(o2) : o1 === o2;
  }

  addJourneyEastToCollectionIfMissing<Type extends Pick<IJourneyEast, 'id'>>(
    journeyEastCollection: Type[],
    ...journeyEastsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const journeyEasts: Type[] = journeyEastsToCheck.filter(isPresent);
    if (journeyEasts.length > 0) {
      const journeyEastCollectionIdentifiers = journeyEastCollection.map(
        journeyEastItem => this.getJourneyEastIdentifier(journeyEastItem)!
      );
      const journeyEastsToAdd = journeyEasts.filter(journeyEastItem => {
        const journeyEastIdentifier = this.getJourneyEastIdentifier(journeyEastItem);
        if (journeyEastCollectionIdentifiers.includes(journeyEastIdentifier)) {
          return false;
        }
        journeyEastCollectionIdentifiers.push(journeyEastIdentifier);
        return true;
      });
      return [...journeyEastsToAdd, ...journeyEastCollection];
    }
    return journeyEastCollection;
  }

  protected convertDateFromClient<T extends IJourneyEast | NewJourneyEast | PartialUpdateJourneyEast>(journeyEast: T): RestOf<T> {
    return {
      ...journeyEast,
      actualDepartureTime: journeyEast.actualDepartureTime?.toJSON() ?? null,
      plannedDepartureTime: journeyEast.plannedDepartureTime?.toJSON() ?? null,
      actualArrivalTime: journeyEast.actualArrivalTime?.toJSON() ?? null,
      plannedArrivalTime: journeyEast.plannedArrivalTime?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restJourneyEast: RestJourneyEast): IJourneyEast {
    return {
      ...restJourneyEast,
      actualDepartureTime: restJourneyEast.actualDepartureTime ? dayjs(restJourneyEast.actualDepartureTime) : undefined,
      plannedDepartureTime: restJourneyEast.plannedDepartureTime ? dayjs(restJourneyEast.plannedDepartureTime) : undefined,
      actualArrivalTime: restJourneyEast.actualArrivalTime ? dayjs(restJourneyEast.actualArrivalTime) : undefined,
      plannedArrivalTime: restJourneyEast.plannedArrivalTime ? dayjs(restJourneyEast.plannedArrivalTime) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestJourneyEast>): HttpResponse<IJourneyEast> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestJourneyEast[]>): HttpResponse<IJourneyEast[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
