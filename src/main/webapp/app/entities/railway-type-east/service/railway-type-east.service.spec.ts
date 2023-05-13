import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRailwayTypeEast } from '../railway-type-east.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../railway-type-east.test-samples';

import { RailwayTypeEastService } from './railway-type-east.service';

const requireRestSample: IRailwayTypeEast = {
  ...sampleWithRequiredData,
};

describe('RailwayTypeEast Service', () => {
  let service: RailwayTypeEastService;
  let httpMock: HttpTestingController;
  let expectedResult: IRailwayTypeEast | IRailwayTypeEast[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RailwayTypeEastService);
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

    it('should create a RailwayTypeEast', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const railwayTypeEast = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(railwayTypeEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RailwayTypeEast', () => {
      const railwayTypeEast = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(railwayTypeEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RailwayTypeEast', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RailwayTypeEast', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RailwayTypeEast', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRailwayTypeEastToCollectionIfMissing', () => {
      it('should add a RailwayTypeEast to an empty array', () => {
        const railwayTypeEast: IRailwayTypeEast = sampleWithRequiredData;
        expectedResult = service.addRailwayTypeEastToCollectionIfMissing([], railwayTypeEast);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(railwayTypeEast);
      });

      it('should not add a RailwayTypeEast to an array that contains it', () => {
        const railwayTypeEast: IRailwayTypeEast = sampleWithRequiredData;
        const railwayTypeEastCollection: IRailwayTypeEast[] = [
          {
            ...railwayTypeEast,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRailwayTypeEastToCollectionIfMissing(railwayTypeEastCollection, railwayTypeEast);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RailwayTypeEast to an array that doesn't contain it", () => {
        const railwayTypeEast: IRailwayTypeEast = sampleWithRequiredData;
        const railwayTypeEastCollection: IRailwayTypeEast[] = [sampleWithPartialData];
        expectedResult = service.addRailwayTypeEastToCollectionIfMissing(railwayTypeEastCollection, railwayTypeEast);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(railwayTypeEast);
      });

      it('should add only unique RailwayTypeEast to an array', () => {
        const railwayTypeEastArray: IRailwayTypeEast[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const railwayTypeEastCollection: IRailwayTypeEast[] = [sampleWithRequiredData];
        expectedResult = service.addRailwayTypeEastToCollectionIfMissing(railwayTypeEastCollection, ...railwayTypeEastArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const railwayTypeEast: IRailwayTypeEast = sampleWithRequiredData;
        const railwayTypeEast2: IRailwayTypeEast = sampleWithPartialData;
        expectedResult = service.addRailwayTypeEastToCollectionIfMissing([], railwayTypeEast, railwayTypeEast2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(railwayTypeEast);
        expect(expectedResult).toContain(railwayTypeEast2);
      });

      it('should accept null and undefined values', () => {
        const railwayTypeEast: IRailwayTypeEast = sampleWithRequiredData;
        expectedResult = service.addRailwayTypeEastToCollectionIfMissing([], null, railwayTypeEast, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(railwayTypeEast);
      });

      it('should return initial array if no RailwayTypeEast is added', () => {
        const railwayTypeEastCollection: IRailwayTypeEast[] = [sampleWithRequiredData];
        expectedResult = service.addRailwayTypeEastToCollectionIfMissing(railwayTypeEastCollection, undefined, null);
        expect(expectedResult).toEqual(railwayTypeEastCollection);
      });
    });

    describe('compareRailwayTypeEast', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRailwayTypeEast(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRailwayTypeEast(entity1, entity2);
        const compareResult2 = service.compareRailwayTypeEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRailwayTypeEast(entity1, entity2);
        const compareResult2 = service.compareRailwayTypeEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRailwayTypeEast(entity1, entity2);
        const compareResult2 = service.compareRailwayTypeEast(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
