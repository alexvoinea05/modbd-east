import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITicketEast, NewTicketEast } from '../ticket-east.model';

export type PartialUpdateTicketEast = Partial<ITicketEast> & Pick<ITicketEast, 'id'>;

type RestOf<T extends ITicketEast | NewTicketEast> = Omit<T, 'time'> & {
  time?: string | null;
};

export type RestTicketEast = RestOf<ITicketEast>;

export type NewRestTicketEast = RestOf<NewTicketEast>;

export type PartialUpdateRestTicketEast = RestOf<PartialUpdateTicketEast>;

export type EntityResponseType = HttpResponse<ITicketEast>;
export type EntityArrayResponseType = HttpResponse<ITicketEast[]>;

@Injectable({ providedIn: 'root' })
export class TicketEastService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ticket-easts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ticketEast: NewTicketEast): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ticketEast);
    return this.http
      .post<RestTicketEast>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(ticketEast: ITicketEast): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ticketEast);
    return this.http
      .put<RestTicketEast>(`${this.resourceUrl}/${this.getTicketEastIdentifier(ticketEast)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(ticketEast: PartialUpdateTicketEast): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ticketEast);
    return this.http
      .patch<RestTicketEast>(`${this.resourceUrl}/${this.getTicketEastIdentifier(ticketEast)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTicketEast>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTicketEast[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTicketEastIdentifier(ticketEast: Pick<ITicketEast, 'id'>): number {
    return ticketEast.id;
  }

  compareTicketEast(o1: Pick<ITicketEast, 'id'> | null, o2: Pick<ITicketEast, 'id'> | null): boolean {
    return o1 && o2 ? this.getTicketEastIdentifier(o1) === this.getTicketEastIdentifier(o2) : o1 === o2;
  }

  addTicketEastToCollectionIfMissing<Type extends Pick<ITicketEast, 'id'>>(
    ticketEastCollection: Type[],
    ...ticketEastsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ticketEasts: Type[] = ticketEastsToCheck.filter(isPresent);
    if (ticketEasts.length > 0) {
      const ticketEastCollectionIdentifiers = ticketEastCollection.map(ticketEastItem => this.getTicketEastIdentifier(ticketEastItem)!);
      const ticketEastsToAdd = ticketEasts.filter(ticketEastItem => {
        const ticketEastIdentifier = this.getTicketEastIdentifier(ticketEastItem);
        if (ticketEastCollectionIdentifiers.includes(ticketEastIdentifier)) {
          return false;
        }
        ticketEastCollectionIdentifiers.push(ticketEastIdentifier);
        return true;
      });
      return [...ticketEastsToAdd, ...ticketEastCollection];
    }
    return ticketEastCollection;
  }

  protected convertDateFromClient<T extends ITicketEast | NewTicketEast | PartialUpdateTicketEast>(ticketEast: T): RestOf<T> {
    return {
      ...ticketEast,
      time: ticketEast.time?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTicketEast: RestTicketEast): ITicketEast {
    return {
      ...restTicketEast,
      time: restTicketEast.time ? dayjs(restTicketEast.time) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTicketEast>): HttpResponse<ITicketEast> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTicketEast[]>): HttpResponse<ITicketEast[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
