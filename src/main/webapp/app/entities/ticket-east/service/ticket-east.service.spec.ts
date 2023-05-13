import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITicketEast } from '../ticket-east.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../ticket-east.test-samples';

import { TicketEastService, RestTicketEast } from './ticket-east.service';

const requireRestSample: RestTicketEast = {
  ...sampleWithRequiredData,
  time: sampleWithRequiredData.time?.toJSON(),
};

describe('TicketEast Service', () => {
  let service: TicketEastService;
  let httpMock: HttpTestingController;
  let expectedResult: ITicketEast | ITicketEast[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TicketEastService);
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

    it('should create a TicketEast', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const ticketEast = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(ticketEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TicketEast', () => {
      const ticketEast = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(ticketEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TicketEast', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TicketEast', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TicketEast', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTicketEastToCollectionIfMissing', () => {
      it('should add a TicketEast to an empty array', () => {
        const ticketEast: ITicketEast = sampleWithRequiredData;
        expectedResult = service.addTicketEastToCollectionIfMissing([], ticketEast);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ticketEast);
      });

      it('should not add a TicketEast to an array that contains it', () => {
        const ticketEast: ITicketEast = sampleWithRequiredData;
        const ticketEastCollection: ITicketEast[] = [
          {
            ...ticketEast,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTicketEastToCollectionIfMissing(ticketEastCollection, ticketEast);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TicketEast to an array that doesn't contain it", () => {
        const ticketEast: ITicketEast = sampleWithRequiredData;
        const ticketEastCollection: ITicketEast[] = [sampleWithPartialData];
        expectedResult = service.addTicketEastToCollectionIfMissing(ticketEastCollection, ticketEast);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ticketEast);
      });

      it('should add only unique TicketEast to an array', () => {
        const ticketEastArray: ITicketEast[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const ticketEastCollection: ITicketEast[] = [sampleWithRequiredData];
        expectedResult = service.addTicketEastToCollectionIfMissing(ticketEastCollection, ...ticketEastArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ticketEast: ITicketEast = sampleWithRequiredData;
        const ticketEast2: ITicketEast = sampleWithPartialData;
        expectedResult = service.addTicketEastToCollectionIfMissing([], ticketEast, ticketEast2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ticketEast);
        expect(expectedResult).toContain(ticketEast2);
      });

      it('should accept null and undefined values', () => {
        const ticketEast: ITicketEast = sampleWithRequiredData;
        expectedResult = service.addTicketEastToCollectionIfMissing([], null, ticketEast, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ticketEast);
      });

      it('should return initial array if no TicketEast is added', () => {
        const ticketEastCollection: ITicketEast[] = [sampleWithRequiredData];
        expectedResult = service.addTicketEastToCollectionIfMissing(ticketEastCollection, undefined, null);
        expect(expectedResult).toEqual(ticketEastCollection);
      });
    });

    describe('compareTicketEast', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTicketEast(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTicketEast(entity1, entity2);
        const compareResult2 = service.compareTicketEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTicketEast(entity1, entity2);
        const compareResult2 = service.compareTicketEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTicketEast(entity1, entity2);
        const compareResult2 = service.compareTicketEast(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
