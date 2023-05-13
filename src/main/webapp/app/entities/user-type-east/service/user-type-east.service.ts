import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserTypeEast, NewUserTypeEast } from '../user-type-east.model';

export type PartialUpdateUserTypeEast = Partial<IUserTypeEast> & Pick<IUserTypeEast, 'id'>;

export type EntityResponseType = HttpResponse<IUserTypeEast>;
export type EntityArrayResponseType = HttpResponse<IUserTypeEast[]>;

@Injectable({ providedIn: 'root' })
export class UserTypeEastService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-type-easts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userTypeEast: NewUserTypeEast): Observable<EntityResponseType> {
    return this.http.post<IUserTypeEast>(this.resourceUrl, userTypeEast, { observe: 'response' });
  }

  update(userTypeEast: IUserTypeEast): Observable<EntityResponseType> {
    return this.http.put<IUserTypeEast>(`${this.resourceUrl}/${this.getUserTypeEastIdentifier(userTypeEast)}`, userTypeEast, {
      observe: 'response',
    });
  }

  partialUpdate(userTypeEast: PartialUpdateUserTypeEast): Observable<EntityResponseType> {
    return this.http.patch<IUserTypeEast>(`${this.resourceUrl}/${this.getUserTypeEastIdentifier(userTypeEast)}`, userTypeEast, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserTypeEast>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserTypeEast[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUserTypeEastIdentifier(userTypeEast: Pick<IUserTypeEast, 'id'>): number {
    return userTypeEast.id;
  }

  compareUserTypeEast(o1: Pick<IUserTypeEast, 'id'> | null, o2: Pick<IUserTypeEast, 'id'> | null): boolean {
    return o1 && o2 ? this.getUserTypeEastIdentifier(o1) === this.getUserTypeEastIdentifier(o2) : o1 === o2;
  }

  addUserTypeEastToCollectionIfMissing<Type extends Pick<IUserTypeEast, 'id'>>(
    userTypeEastCollection: Type[],
    ...userTypeEastsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const userTypeEasts: Type[] = userTypeEastsToCheck.filter(isPresent);
    if (userTypeEasts.length > 0) {
      const userTypeEastCollectionIdentifiers = userTypeEastCollection.map(
        userTypeEastItem => this.getUserTypeEastIdentifier(userTypeEastItem)!
      );
      const userTypeEastsToAdd = userTypeEasts.filter(userTypeEastItem => {
        const userTypeEastIdentifier = this.getUserTypeEastIdentifier(userTypeEastItem);
        if (userTypeEastCollectionIdentifiers.includes(userTypeEastIdentifier)) {
          return false;
        }
        userTypeEastCollectionIdentifiers.push(userTypeEastIdentifier);
        return true;
      });
      return [...userTypeEastsToAdd, ...userTypeEastCollection];
    }
    return userTypeEastCollection;
  }
}
