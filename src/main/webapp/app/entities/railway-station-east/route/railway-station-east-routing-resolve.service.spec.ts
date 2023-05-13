import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IRailwayStationEast } from '../railway-station-east.model';
import { RailwayStationEastService } from '../service/railway-station-east.service';

import { RailwayStationEastRoutingResolveService } from './railway-station-east-routing-resolve.service';

describe('RailwayStationEast routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: RailwayStationEastRoutingResolveService;
  let service: RailwayStationEastService;
  let resultRailwayStationEast: IRailwayStationEast | null | undefined;

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
    routingResolveService = TestBed.inject(RailwayStationEastRoutingResolveService);
    service = TestBed.inject(RailwayStationEastService);
    resultRailwayStationEast = undefined;
  });

  describe('resolve', () => {
    it('should return IRailwayStationEast returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRailwayStationEast = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRailwayStationEast).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRailwayStationEast = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultRailwayStationEast).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IRailwayStationEast>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRailwayStationEast = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRailwayStationEast).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
