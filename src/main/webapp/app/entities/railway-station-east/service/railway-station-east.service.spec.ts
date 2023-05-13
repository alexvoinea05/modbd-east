import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRailwayStationEast } from '../railway-station-east.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../railway-station-east.test-samples';

import { RailwayStationEastService } from './railway-station-east.service';

const requireRestSample: IRailwayStationEast = {
  ...sampleWithRequiredData,
};

describe('RailwayStationEast Service', () => {
  let service: RailwayStationEastService;
  let httpMock: HttpTestingController;
  let expectedResult: IRailwayStationEast | IRailwayStationEast[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RailwayStationEastService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a RailwayStationEast', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const railwayStationEast = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(railwayStationEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RailwayStationEast', () => {
      const railwayStationEast = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(railwayStationEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RailwayStationEast', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RailwayStationEast', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RailwayStationEast', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRailwayStationEastToCollectionIfMissing', () => {
      it('should add a RailwayStationEast to an empty array', () => {
        const railwayStationEast: IRailwayStationEast = sampleWithRequiredData;
        expectedResult = service.addRailwayStationEastToCollectionIfMissing([], railwayStationEast);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(railwayStationEast);
      });

      it('should not add a RailwayStationEast to an array that contains it', () => {
        const railwayStationEast: IRailwayStationEast = sampleWithRequiredData;
        const railwayStationEastCollection: IRailwayStationEast[] = [
          {
            ...railwayStationEast,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRailwayStationEastToCollectionIfMissing(railwayStationEastCollection, railwayStationEast);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RailwayStationEast to an array that doesn't contain it", () => {
        const railwayStationEast: IRailwayStationEast = sampleWithRequiredData;
        const railwayStationEastCollection: IRailwayStationEast[] = [sampleWithPartialData];
        expectedResult = service.addRailwayStationEastToCollectionIfMissing(railwayStationEastCollection, railwayStationEast);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(railwayStationEast);
      });

      it('should add only unique RailwayStationEast to an array', () => {
        const railwayStationEastArray: IRailwayStationEast[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const railwayStationEastCollection: IRailwayStationEast[] = [sampleWithRequiredData];
        expectedResult = service.addRailwayStationEastToCollectionIfMissing(railwayStationEastCollection, ...railwayStationEastArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const railwayStationEast: IRailwayStationEast = sampleWithRequiredData;
        const railwayStationEast2: IRailwayStationEast = sampleWithPartialData;
        expectedResult = service.addRailwayStationEastToCollectionIfMissing([], railwayStationEast, railwayStationEast2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(railwayStationEast);
        expect(expectedResult).toContain(railwayStationEast2);
      });

      it('should accept null and undefined values', () => {
        const railwayStationEast: IRailwayStationEast = sampleWithRequiredData;
        expectedResult = service.addRailwayStationEastToCollectionIfMissing([], null, railwayStationEast, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(railwayStationEast);
      });

      it('should return initial array if no RailwayStationEast is added', () => {
        const railwayStationEastCollection: IRailwayStationEast[] = [sampleWithRequiredData];
        expectedResult = service.addRailwayStationEastToCollectionIfMissing(railwayStationEastCollection, undefined, null);
        expect(expectedResult).toEqual(railwayStationEastCollection);
      });
    });

    describe('compareRailwayStationEast', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRailwayStationEast(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRailwayStationEast(entity1, entity2);
        const compareResult2 = service.compareRailwayStationEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRailwayStationEast(entity1, entity2);
        const compareResult2 = service.compareRailwayStationEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRailwayStationEast(entity1, entity2);
        const compareResult2 = service.compareRailwayStationEast(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
