import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITrainEast, NewTrainEast } from '../train-east.model';

export type PartialUpdateTrainEast = Partial<ITrainEast> & Pick<ITrainEast, 'id'>;

export type EntityResponseType = HttpResponse<ITrainEast>;
export type EntityArrayResponseType = HttpResponse<ITrainEast[]>;

@Injectable({ providedIn: 'root' })
export class TrainEastService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/train-easts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(trainEast: NewTrainEast): Observable<EntityResponseType> {
    return this.http.post<ITrainEast>(this.resourceUrl, trainEast, { observe: 'response' });
  }

  update(trainEast: ITrainEast): Observable<EntityResponseType> {
    return this.http.put<ITrainEast>(`${this.resourceUrl}/${this.getTrainEastIdentifier(trainEast)}`, trainEast, { observe: 'response' });
  }

  partialUpdate(trainEast: PartialUpdateTrainEast): Observable<EntityResponseType> {
    return this.http.patch<ITrainEast>(`${this.resourceUrl}/${this.getTrainEastIdentifier(trainEast)}`, trainEast, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrainEast>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainEast[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTrainEastIdentifier(trainEast: Pick<ITrainEast, 'id'>): number {
    return trainEast.id;
  }

  compareTrainEast(o1: Pick<ITrainEast, 'id'> | null, o2: Pick<ITrainEast, 'id'> | null): boolean {
    return o1 && o2 ? this.getTrainEastIdentifier(o1) === this.getTrainEastIdentifier(o2) : o1 === o2;
  }

  addTrainEastToCollectionIfMissing<Type extends Pick<ITrainEast, 'id'>>(
    trainEastCollection: Type[],
    ...trainEastsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const trainEasts: Type[] = trainEastsToCheck.filter(isPresent);
    if (trainEasts.length > 0) {
      const trainEastCollectionIdentifiers = trainEastCollection.map(trainEastItem => this.getTrainEastIdentifier(trainEastItem)!);
      const trainEastsToAdd = trainEasts.filter(trainEastItem => {
        const trainEastIdentifier = this.getTrainEastIdentifier(trainEastItem);
        if (trainEastCollectionIdentifiers.includes(trainEastIdentifier)) {
          return false;
        }
        trainEastCollectionIdentifiers.push(trainEastIdentifier);
        return true;
      });
      return [...trainEastsToAdd, ...trainEastCollection];
    }
    return trainEastCollection;
  }
}
