import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICompanyEast, NewCompanyEast } from '../company-east.model';

export type PartialUpdateCompanyEast = Partial<ICompanyEast> & Pick<ICompanyEast, 'id'>;

export type EntityResponseType = HttpResponse<ICompanyEast>;
export type EntityArrayResponseType = HttpResponse<ICompanyEast[]>;

@Injectable({ providedIn: 'root' })
export class CompanyEastService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/company-easts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(companyEast: NewCompanyEast): Observable<EntityResponseType> {
    return this.http.post<ICompanyEast>(this.resourceUrl, companyEast, { observe: 'response' });
  }

  update(companyEast: ICompanyEast): Observable<EntityResponseType> {
    return this.http.put<ICompanyEast>(`${this.resourceUrl}/${this.getCompanyEastIdentifier(companyEast)}`, companyEast, {
      observe: 'response',
    });
  }

  partialUpdate(companyEast: PartialUpdateCompanyEast): Observable<EntityResponseType> {
    return this.http.patch<ICompanyEast>(`${this.resourceUrl}/${this.getCompanyEastIdentifier(companyEast)}`, companyEast, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompanyEast>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanyEast[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCompanyEastIdentifier(companyEast: Pick<ICompanyEast, 'id'>): number {
    return companyEast.id;
  }

  compareCompanyEast(o1: Pick<ICompanyEast, 'id'> | null, o2: Pick<ICompanyEast, 'id'> | null): boolean {
    return o1 && o2 ? this.getCompanyEastIdentifier(o1) === this.getCompanyEastIdentifier(o2) : o1 === o2;
  }

  addCompanyEastToCollectionIfMissing<Type extends Pick<ICompanyEast, 'id'>>(
    companyEastCollection: Type[],
    ...companyEastsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const companyEasts: Type[] = companyEastsToCheck.filter(isPresent);
    if (companyEasts.length > 0) {
      const companyEastCollectionIdentifiers = companyEastCollection.map(
        companyEastItem => this.getCompanyEastIdentifier(companyEastItem)!
      );
      const companyEastsToAdd = companyEasts.filter(companyEastItem => {
        const companyEastIdentifier = this.getCompanyEastIdentifier(companyEastItem);
        if (companyEastCollectionIdentifiers.includes(companyEastIdentifier)) {
          return false;
        }
        companyEastCollectionIdentifiers.push(companyEastIdentifier);
        return true;
      });
      return [...companyEastsToAdd, ...companyEastCollection];
    }
    return companyEastCollection;
  }
}
