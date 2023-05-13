import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAddressEast, NewAddressEast } from '../address-east.model';

export type PartialUpdateAddressEast = Partial<IAddressEast> & Pick<IAddressEast, 'id'>;

export type EntityResponseType = HttpResponse<IAddressEast>;
export type EntityArrayResponseType = HttpResponse<IAddressEast[]>;

@Injectable({ providedIn: 'root' })
export class AddressEastService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/address-easts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(addressEast: NewAddressEast): Observable<EntityResponseType> {
    return this.http.post<IAddressEast>(this.resourceUrl, addressEast, { observe: 'response' });
  }

  update(addressEast: IAddressEast): Observable<EntityResponseType> {
    return this.http.put<IAddressEast>(`${this.resourceUrl}/${this.getAddressEastIdentifier(addressEast)}`, addressEast, {
      observe: 'response',
    });
  }

  partialUpdate(addressEast: PartialUpdateAddressEast): Observable<EntityResponseType> {
    return this.http.patch<IAddressEast>(`${this.resourceUrl}/${this.getAddressEastIdentifier(addressEast)}`, addressEast, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAddressEast>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAddressEast[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAddressEastIdentifier(addressEast: Pick<IAddressEast, 'id'>): number {
    return addressEast.id;
  }

  compareAddressEast(o1: Pick<IAddressEast, 'id'> | null, o2: Pick<IAddressEast, 'id'> | null): boolean {
    return o1 && o2 ? this.getAddressEastIdentifier(o1) === this.getAddressEastIdentifier(o2) : o1 === o2;
  }

  addAddressEastToCollectionIfMissing<Type extends Pick<IAddressEast, 'id'>>(
    addressEastCollection: Type[],
    ...addressEastsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const addressEasts: Type[] = addressEastsToCheck.filter(isPresent);
    if (addressEasts.length > 0) {
      const addressEastCollectionIdentifiers = addressEastCollection.map(
        addressEastItem => this.getAddressEastIdentifier(addressEastItem)!
      );
      const addressEastsToAdd = addressEasts.filter(addressEastItem => {
        const addressEastIdentifier = this.getAddressEastIdentifier(addressEastItem);
        if (addressEastCollectionIdentifiers.includes(addressEastIdentifier)) {
          return false;
        }
        addressEastCollectionIdentifiers.push(addressEastIdentifier);
        return true;
      });
      return [...addressEastsToAdd, ...addressEastCollection];
    }
    return addressEastCollection;
  }
}
