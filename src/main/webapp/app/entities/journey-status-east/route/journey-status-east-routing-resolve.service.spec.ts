import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IJourneyStatusEast } from '../journey-status-east.model';
import { JourneyStatusEastService } from '../service/journey-status-east.service';

import { JourneyStatusEastRoutingResolveService } from './journey-status-east-routing-resolve.service';

describe('JourneyStatusEast routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: JourneyStatusEastRoutingResolveService;
  let service: JourneyStatusEastService;
  let resultJourneyStatusEast: IJourneyStatusEast | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(JourneyStatusEastRoutingResolveService);
    service = TestBed.inject(JourneyStatusEastService);
    resultJourneyStatusEast = undefined;
  });

  describe('resolve', () => {
    it('should return IJourneyStatusEast returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultJourneyStatusEast = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultJourneyStatusEast).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultJourneyStatusEast = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultJourneyStatusEast).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IJourneyStatusEast>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultJourneyStatusEast = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultJourneyStatusEast).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
