import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IJourneyEast } from '../journey-east.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../journey-east.test-samples';

import { JourneyEastService, RestJourneyEast } from './journey-east.service';

const requireRestSample: RestJourneyEast = {
  ...sampleWithRequiredData,
  actualDepartureTime: sampleWithRequiredData.actualDepartureTime?.toJSON(),
  plannedDepartureTime: sampleWithRequiredData.plannedDepartureTime?.toJSON(),
  actualArrivalTime: sampleWithRequiredData.actualArrivalTime?.toJSON(),
  plannedArrivalTime: sampleWithRequiredData.plannedArrivalTime?.toJSON(),
};

describe('JourneyEast Service', () => {
  let service: JourneyEastService;
  let httpMock: HttpTestingController;
  let expectedResult: IJourneyEast | IJourneyEast[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(JourneyEastService);
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

    it('should create a JourneyEast', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const journeyEast = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(journeyEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a JourneyEast', () => {
      const journeyEast = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(journeyEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a JourneyEast', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of JourneyEast', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a JourneyEast', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addJourneyEastToCollectionIfMissing', () => {
      it('should add a JourneyEast to an empty array', () => {
        const journeyEast: IJourneyEast = sampleWithRequiredData;
        expectedResult = service.addJourneyEastToCollectionIfMissing([], journeyEast);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(journeyEast);
      });

      it('should not add a JourneyEast to an array that contains it', () => {
        const journeyEast: IJourneyEast = sampleWithRequiredData;
        const journeyEastCollection: IJourneyEast[] = [
          {
            ...journeyEast,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addJourneyEastToCollectionIfMissing(journeyEastCollection, journeyEast);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a JourneyEast to an array that doesn't contain it", () => {
        const journeyEast: IJourneyEast = sampleWithRequiredData;
        const journeyEastCollection: IJourneyEast[] = [sampleWithPartialData];
        expectedResult = service.addJourneyEastToCollectionIfMissing(journeyEastCollection, journeyEast);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(journeyEast);
      });

      it('should add only unique JourneyEast to an array', () => {
        const journeyEastArray: IJourneyEast[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const journeyEastCollection: IJourneyEast[] = [sampleWithRequiredData];
        expectedResult = service.addJourneyEastToCollectionIfMissing(journeyEastCollection, ...journeyEastArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const journeyEast: IJourneyEast = sampleWithRequiredData;
        const journeyEast2: IJourneyEast = sampleWithPartialData;
        expectedResult = service.addJourneyEastToCollectionIfMissing([], journeyEast, journeyEast2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(journeyEast);
        expect(expectedResult).toContain(journeyEast2);
      });

      it('should accept null and undefined values', () => {
        const journeyEast: IJourneyEast = sampleWithRequiredData;
        expectedResult = service.addJourneyEastToCollectionIfMissing([], null, journeyEast, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(journeyEast);
      });

      it('should return initial array if no JourneyEast is added', () => {
        const journeyEastCollection: IJourneyEast[] = [sampleWithRequiredData];
        expectedResult = service.addJourneyEastToCollectionIfMissing(journeyEastCollection, undefined, null);
        expect(expectedResult).toEqual(journeyEastCollection);
      });
    });

    describe('compareJourneyEast', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareJourneyEast(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareJourneyEast(entity1, entity2);
        const compareResult2 = service.compareJourneyEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareJourneyEast(entity1, entity2);
        const compareResult2 = service.compareJourneyEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareJourneyEast(entity1, entity2);
        const compareResult2 = service.compareJourneyEast(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
