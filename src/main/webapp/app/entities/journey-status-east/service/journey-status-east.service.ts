import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IJourneyStatusEast, NewJourneyStatusEast } from '../journey-status-east.model';

export type PartialUpdateJourneyStatusEast = Partial<IJourneyStatusEast> & Pick<IJourneyStatusEast, 'id'>;

export type EntityResponseType = HttpResponse<IJourneyStatusEast>;
export type EntityArrayResponseType = HttpResponse<IJourneyStatusEast[]>;

@Injectable({ providedIn: 'root' })
export class JourneyStatusEastService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/journey-status-easts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(journeyStatusEast: NewJourneyStatusEast): Observable<EntityResponseType> {
    return this.http.post<IJourneyStatusEast>(this.resourceUrl, journeyStatusEast, { observe: 'response' });
  }

  update(journeyStatusEast: IJourneyStatusEast): Observable<EntityResponseType> {
    return this.http.put<IJourneyStatusEast>(
      `${this.resourceUrl}/${this.getJourneyStatusEastIdentifier(journeyStatusEast)}`,
      journeyStatusEast,
      { observe: 'response' }
    );
  }

  partialUpdate(journeyStatusEast: PartialUpdateJourneyStatusEast): Observable<EntityResponseType> {
    return this.http.patch<IJourneyStatusEast>(
      `${this.resourceUrl}/${this.getJourneyStatusEastIdentifier(journeyStatusEast)}`,
      journeyStatusEast,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IJourneyStatusEast>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJourneyStatusEast[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getJourneyStatusEastIdentifier(journeyStatusEast: Pick<IJourneyStatusEast, 'id'>): number {
    return journeyStatusEast.id;
  }

  compareJourneyStatusEast(o1: Pick<IJourneyStatusEast, 'id'> | null, o2: Pick<IJourneyStatusEast, 'id'> | null): boolean {
    return o1 && o2 ? this.getJourneyStatusEastIdentifier(o1) === this.getJourneyStatusEastIdentifier(o2) : o1 === o2;
  }

  addJourneyStatusEastToCollectionIfMissing<Type extends Pick<IJourneyStatusEast, 'id'>>(
    journeyStatusEastCollection: Type[],
    ...journeyStatusEastsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const journeyStatusEasts: Type[] = journeyStatusEastsToCheck.filter(isPresent);
    if (journeyStatusEasts.length > 0) {
      const journeyStatusEastCollectionIdentifiers = journeyStatusEastCollection.map(
        journeyStatusEastItem => this.getJourneyStatusEastIdentifier(journeyStatusEastItem)!
      );
      const journeyStatusEastsToAdd = journeyStatusEasts.filter(journeyStatusEastItem => {
        const journeyStatusEastIdentifier = this.getJourneyStatusEastIdentifier(journeyStatusEastItem);
        if (journeyStatusEastCollectionIdentifiers.includes(journeyStatusEastIdentifier)) {
          return false;
        }
        journeyStatusEastCollectionIdentifiers.push(journeyStatusEastIdentifier);
        return true;
      });
      return [...journeyStatusEastsToAdd, ...journeyStatusEastCollection];
    }
    return journeyStatusEastCollection;
  }
}
