import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDistrictEast, NewDistrictEast } from '../district-east.model';

export type PartialUpdateDistrictEast = Partial<IDistrictEast> & Pick<IDistrictEast, 'id'>;

export type EntityResponseType = HttpResponse<IDistrictEast>;
export type EntityArrayResponseType = HttpResponse<IDistrictEast[]>;

@Injectable({ providedIn: 'root' })
export class DistrictEastService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/district-easts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(districtEast: NewDistrictEast): Observable<EntityResponseType> {
    return this.http.post<IDistrictEast>(this.resourceUrl, districtEast, { observe: 'response' });
  }

  update(districtEast: IDistrictEast): Observable<EntityResponseType> {
    return this.http.put<IDistrictEast>(`${this.resourceUrl}/${this.getDistrictEastIdentifier(districtEast)}`, districtEast, {
      observe: 'response',
    });
  }

  partialUpdate(districtEast: PartialUpdateDistrictEast): Observable<EntityResponseType> {
    return this.http.patch<IDistrictEast>(`${this.resourceUrl}/${this.getDistrictEastIdentifier(districtEast)}`, districtEast, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDistrictEast>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDistrictEast[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDistrictEastIdentifier(districtEast: Pick<IDistrictEast, 'id'>): number {
    return districtEast.id;
  }

  compareDistrictEast(o1: Pick<IDistrictEast, 'id'> | null, o2: Pick<IDistrictEast, 'id'> | null): boolean {
    return o1 && o2 ? this.getDistrictEastIdentifier(o1) === this.getDistrictEastIdentifier(o2) : o1 === o2;
  }

  addDistrictEastToCollectionIfMissing<Type extends Pick<IDistrictEast, 'id'>>(
    districtEastCollection: Type[],
    ...districtEastsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const districtEasts: Type[] = districtEastsToCheck.filter(isPresent);
    if (districtEasts.length > 0) {
      const districtEastCollectionIdentifiers = districtEastCollection.map(
        districtEastItem => this.getDistrictEastIdentifier(districtEastItem)!
      );
      const districtEastsToAdd = districtEasts.filter(districtEastItem => {
        const districtEastIdentifier = this.getDistrictEastIdentifier(districtEastItem);
        if (districtEastCollectionIdentifiers.includes(districtEastIdentifier)) {
          return false;
        }
        districtEastCollectionIdentifiers.push(districtEastIdentifier);
        return true;
      });
      return [...districtEastsToAdd, ...districtEastCollection];
    }
    return districtEastCollection;
  }
}
