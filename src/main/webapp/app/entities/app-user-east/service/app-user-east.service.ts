import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAppUserEast, NewAppUserEast } from '../app-user-east.model';

export type PartialUpdateAppUserEast = Partial<IAppUserEast> & Pick<IAppUserEast, 'id'>;

export type EntityResponseType = HttpResponse<IAppUserEast>;
export type EntityArrayResponseType = HttpResponse<IAppUserEast[]>;

@Injectable({ providedIn: 'root' })
export class AppUserEastService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/app-user-easts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(appUserEast: NewAppUserEast): Observable<EntityResponseType> {
    return this.http.post<IAppUserEast>(this.resourceUrl, appUserEast, { observe: 'response' });
  }

  update(appUserEast: IAppUserEast): Observable<EntityResponseType> {
    return this.http.put<IAppUserEast>(`${this.resourceUrl}/${this.getAppUserEastIdentifier(appUserEast)}`, appUserEast, {
      observe: 'response',
    });
  }

  partialUpdate(appUserEast: PartialUpdateAppUserEast): Observable<EntityResponseType> {
    return this.http.patch<IAppUserEast>(`${this.resourceUrl}/${this.getAppUserEastIdentifier(appUserEast)}`, appUserEast, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAppUserEast>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAppUserEast[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAppUserEastIdentifier(appUserEast: Pick<IAppUserEast, 'id'>): number {
    return appUserEast.id;
  }

  compareAppUserEast(o1: Pick<IAppUserEast, 'id'> | null, o2: Pick<IAppUserEast, 'id'> | null): boolean {
    return o1 && o2 ? this.getAppUserEastIdentifier(o1) === this.getAppUserEastIdentifier(o2) : o1 === o2;
  }

  addAppUserEastToCollectionIfMissing<Type extends Pick<IAppUserEast, 'id'>>(
    appUserEastCollection: Type[],
    ...appUserEastsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const appUserEasts: Type[] = appUserEastsToCheck.filter(isPresent);
    if (appUserEasts.length > 0) {
      const appUserEastCollectionIdentifiers = appUserEastCollection.map(
        appUserEastItem => this.getAppUserEastIdentifier(appUserEastItem)!
      );
      const appUserEastsToAdd = appUserEasts.filter(appUserEastItem => {
        const appUserEastIdentifier = this.getAppUserEastIdentifier(appUserEastItem);
        if (appUserEastCollectionIdentifiers.includes(appUserEastIdentifier)) {
          return false;
        }
        appUserEastCollectionIdentifiers.push(appUserEastIdentifier);
        return true;
      });
      return [...appUserEastsToAdd, ...appUserEastCollection];
    }
    return appUserEastCollection;
  }
}
