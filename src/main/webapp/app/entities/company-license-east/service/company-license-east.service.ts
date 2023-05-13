import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICompanyLicenseEast, NewCompanyLicenseEast } from '../company-license-east.model';

export type PartialUpdateCompanyLicenseEast = Partial<ICompanyLicenseEast> & Pick<ICompanyLicenseEast, 'id'>;

export type EntityResponseType = HttpResponse<ICompanyLicenseEast>;
export type EntityArrayResponseType = HttpResponse<ICompanyLicenseEast[]>;

@Injectable({ providedIn: 'root' })
export class CompanyLicenseEastService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/company-license-easts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(companyLicenseEast: NewCompanyLicenseEast): Observable<EntityResponseType> {
    return this.http.post<ICompanyLicenseEast>(this.resourceUrl, companyLicenseEast, { observe: 'response' });
  }

  update(companyLicenseEast: ICompanyLicenseEast): Observable<EntityResponseType> {
    return this.http.put<ICompanyLicenseEast>(
      `${this.resourceUrl}/${this.getCompanyLicenseEastIdentifier(companyLicenseEast)}`,
      companyLicenseEast,
      { observe: 'response' }
    );
  }

  partialUpdate(companyLicenseEast: PartialUpdateCompanyLicenseEast): Observable<EntityResponseType> {
    return this.http.patch<ICompanyLicenseEast>(
      `${this.resourceUrl}/${this.getCompanyLicenseEastIdentifier(companyLicenseEast)}`,
      companyLicenseEast,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompanyLicenseEast>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanyLicenseEast[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCompanyLicenseEastIdentifier(companyLicenseEast: Pick<ICompanyLicenseEast, 'id'>): number {
    return companyLicenseEast.id;
  }

  compareCompanyLicenseEast(o1: Pick<ICompanyLicenseEast, 'id'> | null, o2: Pick<ICompanyLicenseEast, 'id'> | null): boolean {
    return o1 && o2 ? this.getCompanyLicenseEastIdentifier(o1) === this.getCompanyLicenseEastIdentifier(o2) : o1 === o2;
  }

  addCompanyLicenseEastToCollectionIfMissing<Type extends Pick<ICompanyLicenseEast, 'id'>>(
    companyLicenseEastCollection: Type[],
    ...companyLicenseEastsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const companyLicenseEasts: Type[] = companyLicenseEastsToCheck.filter(isPresent);
    if (companyLicenseEasts.length > 0) {
      const companyLicenseEastCollectionIdentifiers = companyLicenseEastCollection.map(
        companyLicenseEastItem => this.getCompanyLicenseEastIdentifier(companyLicenseEastItem)!
      );
      const companyLicenseEastsToAdd = companyLicenseEasts.filter(companyLicenseEastItem => {
        const companyLicenseEastIdentifier = this.getCompanyLicenseEastIdentifier(companyLicenseEastItem);
        if (companyLicenseEastCollectionIdentifiers.includes(companyLicenseEastIdentifier)) {
          return false;
        }
        companyLicenseEastCollectionIdentifiers.push(companyLicenseEastIdentifier);
        return true;
      });
      return [...companyLicenseEastsToAdd, ...companyLicenseEastCollection];
    }
    return companyLicenseEastCollection;
  }
}
