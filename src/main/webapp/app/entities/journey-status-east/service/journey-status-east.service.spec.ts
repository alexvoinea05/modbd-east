import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IJourneyStatusEast } from '../journey-status-east.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../journey-status-east.test-samples';

import { JourneyStatusEastService } from './journey-status-east.service';

const requireRestSample: IJourneyStatusEast = {
  ...sampleWithRequiredData,
};

describe('JourneyStatusEast Service', () => {
  let service: JourneyStatusEastService;
  let httpMock: HttpTestingController;
  let expectedResult: IJourneyStatusEast | IJourneyStatusEast[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(JourneyStatusEastService);
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

    it('should create a JourneyStatusEast', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const journeyStatusEast = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(journeyStatusEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a JourneyStatusEast', () => {
      const journeyStatusEast = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(journeyStatusEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a JourneyStatusEast', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of JourneyStatusEast', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a JourneyStatusEast', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addJourneyStatusEastToCollectionIfMissing', () => {
      it('should add a JourneyStatusEast to an empty array', () => {
        const journeyStatusEast: IJourneyStatusEast = sampleWithRequiredData;
        expectedResult = service.addJourneyStatusEastToCollectionIfMissing([], journeyStatusEast);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(journeyStatusEast);
      });

      it('should not add a JourneyStatusEast to an array that contains it', () => {
        const journeyStatusEast: IJourneyStatusEast = sampleWithRequiredData;
        const journeyStatusEastCollection: IJourneyStatusEast[] = [
          {
            ...journeyStatusEast,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addJourneyStatusEastToCollectionIfMissing(journeyStatusEastCollection, journeyStatusEast);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a JourneyStatusEast to an array that doesn't contain it", () => {
        const journeyStatusEast: IJourneyStatusEast = sampleWithRequiredData;
        const journeyStatusEastCollection: IJourneyStatusEast[] = [sampleWithPartialData];
        expectedResult = service.addJourneyStatusEastToCollectionIfMissing(journeyStatusEastCollection, journeyStatusEast);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(journeyStatusEast);
      });

      it('should add only unique JourneyStatusEast to an array', () => {
        const journeyStatusEastArray: IJourneyStatusEast[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const journeyStatusEastCollection: IJourneyStatusEast[] = [sampleWithRequiredData];
        expectedResult = service.addJourneyStatusEastToCollectionIfMissing(journeyStatusEastCollection, ...journeyStatusEastArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const journeyStatusEast: IJourneyStatusEast = sampleWithRequiredData;
        const journeyStatusEast2: IJourneyStatusEast = sampleWithPartialData;
        expectedResult = service.addJourneyStatusEastToCollectionIfMissing([], journeyStatusEast, journeyStatusEast2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(journeyStatusEast);
        expect(expectedResult).toContain(journeyStatusEast2);
      });

      it('should accept null and undefined values', () => {
        const journeyStatusEast: IJourneyStatusEast = sampleWithRequiredData;
        expectedResult = service.addJourneyStatusEastToCollectionIfMissing([], null, journeyStatusEast, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(journeyStatusEast);
      });

      it('should return initial array if no JourneyStatusEast is added', () => {
        const journeyStatusEastCollection: IJourneyStatusEast[] = [sampleWithRequiredData];
        expectedResult = service.addJourneyStatusEastToCollectionIfMissing(journeyStatusEastCollection, undefined, null);
        expect(expectedResult).toEqual(journeyStatusEastCollection);
      });
    });

    describe('compareJourneyStatusEast', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareJourneyStatusEast(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareJourneyStatusEast(entity1, entity2);
        const compareResult2 = service.compareJourneyStatusEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareJourneyStatusEast(entity1, entity2);
        const compareResult2 = service.compareJourneyStatusEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareJourneyStatusEast(entity1, entity2);
        const compareResult2 = service.compareJourneyStatusEast(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
