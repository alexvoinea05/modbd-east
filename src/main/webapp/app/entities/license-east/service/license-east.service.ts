import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILicenseEast, NewLicenseEast } from '../license-east.model';

export type PartialUpdateLicenseEast = Partial<ILicenseEast> & Pick<ILicenseEast, 'id'>;

export type EntityResponseType = HttpResponse<ILicenseEast>;
export type EntityArrayResponseType = HttpResponse<ILicenseEast[]>;

@Injectable({ providedIn: 'root' })
export class LicenseEastService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/license-easts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(licenseEast: NewLicenseEast): Observable<EntityResponseType> {
    return this.http.post<ILicenseEast>(this.resourceUrl, licenseEast, { observe: 'response' });
  }

  update(licenseEast: ILicenseEast): Observable<EntityResponseType> {
    return this.http.put<ILicenseEast>(`${this.resourceUrl}/${this.getLicenseEastIdentifier(licenseEast)}`, licenseEast, {
      observe: 'response',
    });
  }

  partialUpdate(licenseEast: PartialUpdateLicenseEast): Observable<EntityResponseType> {
    return this.http.patch<ILicenseEast>(`${this.resourceUrl}/${this.getLicenseEastIdentifier(licenseEast)}`, licenseEast, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILicenseEast>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILicenseEast[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLicenseEastIdentifier(licenseEast: Pick<ILicenseEast, 'id'>): number {
    return licenseEast.id;
  }

  compareLicenseEast(o1: Pick<ILicenseEast, 'id'> | null, o2: Pick<ILicenseEast, 'id'> | null): boolean {
    return o1 && o2 ? this.getLicenseEastIdentifier(o1) === this.getLicenseEastIdentifier(o2) : o1 === o2;
  }

  addLicenseEastToCollectionIfMissing<Type extends Pick<ILicenseEast, 'id'>>(
    licenseEastCollection: Type[],
    ...licenseEastsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const licenseEasts: Type[] = licenseEastsToCheck.filter(isPresent);
    if (licenseEasts.length > 0) {
      const licenseEastCollectionIdentifiers = licenseEastCollection.map(
        licenseEastItem => this.getLicenseEastIdentifier(licenseEastItem)!
      );
      const licenseEastsToAdd = licenseEasts.filter(licenseEastItem => {
        const licenseEastIdentifier = this.getLicenseEastIdentifier(licenseEastItem);
        if (licenseEastCollectionIdentifiers.includes(licenseEastIdentifier)) {
          return false;
        }
        licenseEastCollectionIdentifiers.push(licenseEastIdentifier);
        return true;
      });
      return [...licenseEastsToAdd, ...licenseEastCollection];
    }
    return licenseEastCollection;
  }
}
