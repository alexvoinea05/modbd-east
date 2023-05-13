import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IRailwayTypeEast } from '../railway-type-east.model';
import { RailwayTypeEastService } from '../service/railway-type-east.service';

import { RailwayTypeEastRoutingResolveService } from './railway-type-east-routing-resolve.service';

describe('RailwayTypeEast routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: RailwayTypeEastRoutingResolveService;
  let service: RailwayTypeEastService;
  let resultRailwayTypeEast: IRailwayTypeEast | null | undefined;

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
    routingResolveService = TestBed.inject(RailwayTypeEastRoutingResolveService);
    service = TestBed.inject(RailwayTypeEastService);
    resultRailwayTypeEast = undefined;
  });

  describe('resolve', () => {
    it('should return IRailwayTypeEast returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRailwayTypeEast = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRailwayTypeEast).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRailwayTypeEast = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultRailwayTypeEast).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IRailwayTypeEast>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRailwayTypeEast = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRailwayTypeEast).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
