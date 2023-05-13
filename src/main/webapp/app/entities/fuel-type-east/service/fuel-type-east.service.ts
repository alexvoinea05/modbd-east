import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFuelTypeEast, NewFuelTypeEast } from '../fuel-type-east.model';

export type PartialUpdateFuelTypeEast = Partial<IFuelTypeEast> & Pick<IFuelTypeEast, 'id'>;

export type EntityResponseType = HttpResponse<IFuelTypeEast>;
export type EntityArrayResponseType = HttpResponse<IFuelTypeEast[]>;

@Injectable({ providedIn: 'root' })
export class FuelTypeEastService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fuel-type-easts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fuelTypeEast: NewFuelTypeEast): Observable<EntityResponseType> {
    return this.http.post<IFuelTypeEast>(this.resourceUrl, fuelTypeEast, { observe: 'response' });
  }

  update(fuelTypeEast: IFuelTypeEast): Observable<EntityResponseType> {
    return this.http.put<IFuelTypeEast>(`${this.resourceUrl}/${this.getFuelTypeEastIdentifier(fuelTypeEast)}`, fuelTypeEast, {
      observe: 'response',
    });
  }

  partialUpdate(fuelTypeEast: PartialUpdateFuelTypeEast): Observable<EntityResponseType> {
    return this.http.patch<IFuelTypeEast>(`${this.resourceUrl}/${this.getFuelTypeEastIdentifier(fuelTypeEast)}`, fuelTypeEast, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFuelTypeEast>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuelTypeEast[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFuelTypeEastIdentifier(fuelTypeEast: Pick<IFuelTypeEast, 'id'>): number {
    return fuelTypeEast.id;
  }

  compareFuelTypeEast(o1: Pick<IFuelTypeEast, 'id'> | null, o2: Pick<IFuelTypeEast, 'id'> | null): boolean {
    return o1 && o2 ? this.getFuelTypeEastIdentifier(o1) === this.getFuelTypeEastIdentifier(o2) : o1 === o2;
  }

  addFuelTypeEastToCollectionIfMissing<Type extends Pick<IFuelTypeEast, 'id'>>(
    fuelTypeEastCollection: Type[],
    ...fuelTypeEastsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fuelTypeEasts: Type[] = fuelTypeEastsToCheck.filter(isPresent);
    if (fuelTypeEasts.length > 0) {
      const fuelTypeEastCollectionIdentifiers = fuelTypeEastCollection.map(
        fuelTypeEastItem => this.getFuelTypeEastIdentifier(fuelTypeEastItem)!
      );
      const fuelTypeEastsToAdd = fuelTypeEasts.filter(fuelTypeEastItem => {
        const fuelTypeEastIdentifier = this.getFuelTypeEastIdentifier(fuelTypeEastItem);
        if (fuelTypeEastCollectionIdentifiers.includes(fuelTypeEastIdentifier)) {
          return false;
        }
        fuelTypeEastCollectionIdentifiers.push(fuelTypeEastIdentifier);
        return true;
      });
      return [...fuelTypeEastsToAdd, ...fuelTypeEastCollection];
    }
    return fuelTypeEastCollection;
  }
}
