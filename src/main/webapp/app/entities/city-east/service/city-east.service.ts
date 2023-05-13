import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICityEast, NewCityEast } from '../city-east.model';

export type PartialUpdateCityEast = Partial<ICityEast> & Pick<ICityEast, 'id'>;

export type EntityResponseType = HttpResponse<ICityEast>;
export type EntityArrayResponseType = HttpResponse<ICityEast[]>;

@Injectable({ providedIn: 'root' })
export class CityEastService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/city-easts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cityEast: NewCityEast): Observable<EntityResponseType> {
    return this.http.post<ICityEast>(this.resourceUrl, cityEast, { observe: 'response' });
  }

  update(cityEast: ICityEast): Observable<EntityResponseType> {
    return this.http.put<ICityEast>(`${this.resourceUrl}/${this.getCityEastIdentifier(cityEast)}`, cityEast, { observe: 'response' });
  }

  partialUpdate(cityEast: PartialUpdateCityEast): Observable<EntityResponseType> {
    return this.http.patch<ICityEast>(`${this.resourceUrl}/${this.getCityEastIdentifier(cityEast)}`, cityEast, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICityEast>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICityEast[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCityEastIdentifier(cityEast: Pick<ICityEast, 'id'>): number {
    return cityEast.id;
  }

  compareCityEast(o1: Pick<ICityEast, 'id'> | null, o2: Pick<ICityEast, 'id'> | null): boolean {
    return o1 && o2 ? this.getCityEastIdentifier(o1) === this.getCityEastIdentifier(o2) : o1 === o2;
  }

  addCityEastToCollectionIfMissing<Type extends Pick<ICityEast, 'id'>>(
    cityEastCollection: Type[],
    ...cityEastsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cityEasts: Type[] = cityEastsToCheck.filter(isPresent);
    if (cityEasts.length > 0) {
      const cityEastCollectionIdentifiers = cityEastCollection.map(cityEastItem => this.getCityEastIdentifier(cityEastItem)!);
      const cityEastsToAdd = cityEasts.filter(cityEastItem => {
        const cityEastIdentifier = this.getCityEastIdentifier(cityEastItem);
        if (cityEastCollectionIdentifiers.includes(cityEastIdentifier)) {
          return false;
        }
        cityEastCollectionIdentifiers.push(cityEastIdentifier);
        return true;
      });
      return [...cityEastsToAdd, ...cityEastCollection];
    }
    return cityEastCollection;
  }
}
