import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITrainTypeEast, NewTrainTypeEast } from '../train-type-east.model';

export type PartialUpdateTrainTypeEast = Partial<ITrainTypeEast> & Pick<ITrainTypeEast, 'id'>;

export type EntityResponseType = HttpResponse<ITrainTypeEast>;
export type EntityArrayResponseType = HttpResponse<ITrainTypeEast[]>;

@Injectable({ providedIn: 'root' })
export class TrainTypeEastService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/train-type-easts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(trainTypeEast: NewTrainTypeEast): Observable<EntityResponseType> {
    return this.http.post<ITrainTypeEast>(this.resourceUrl, trainTypeEast, { observe: 'response' });
  }

  update(trainTypeEast: ITrainTypeEast): Observable<EntityResponseType> {
    return this.http.put<ITrainTypeEast>(`${this.resourceUrl}/${this.getTrainTypeEastIdentifier(trainTypeEast)}`, trainTypeEast, {
      observe: 'response',
    });
  }

  partialUpdate(trainTypeEast: PartialUpdateTrainTypeEast): Observable<EntityResponseType> {
    return this.http.patch<ITrainTypeEast>(`${this.resourceUrl}/${this.getTrainTypeEastIdentifier(trainTypeEast)}`, trainTypeEast, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrainTypeEast>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainTypeEast[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTrainTypeEastIdentifier(trainTypeEast: Pick<ITrainTypeEast, 'id'>): number {
    return trainTypeEast.id;
  }

  compareTrainTypeEast(o1: Pick<ITrainTypeEast, 'id'> | null, o2: Pick<ITrainTypeEast, 'id'> | null): boolean {
    return o1 && o2 ? this.getTrainTypeEastIdentifier(o1) === this.getTrainTypeEastIdentifier(o2) : o1 === o2;
  }

  addTrainTypeEastToCollectionIfMissing<Type extends Pick<ITrainTypeEast, 'id'>>(
    trainTypeEastCollection: Type[],
    ...trainTypeEastsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const trainTypeEasts: Type[] = trainTypeEastsToCheck.filter(isPresent);
    if (trainTypeEasts.length > 0) {
      const trainTypeEastCollectionIdentifiers = trainTypeEastCollection.map(
        trainTypeEastItem => this.getTrainTypeEastIdentifier(trainTypeEastItem)!
      );
      const trainTypeEastsToAdd = trainTypeEasts.filter(trainTypeEastItem => {
        const trainTypeEastIdentifier = this.getTrainTypeEastIdentifier(trainTypeEastItem);
        if (trainTypeEastCollectionIdentifiers.includes(trainTypeEastIdentifier)) {
          return false;
        }
        trainTypeEastCollectionIdentifiers.push(trainTypeEastIdentifier);
        return true;
      });
      return [...trainTypeEastsToAdd, ...trainTypeEastCollection];
    }
    return trainTypeEastCollection;
  }
}
