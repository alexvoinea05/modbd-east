import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAddressEast } from '../address-east.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../address-east.test-samples';

import { AddressEastService } from './address-east.service';

const requireRestSample: IAddressEast = {
  ...sampleWithRequiredData,
};

describe('AddressEast Service', () => {
  let service: AddressEastService;
  let httpMock: HttpTestingController;
  let expectedResult: IAddressEast | IAddressEast[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AddressEastService);
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

    it('should create a AddressEast', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const addressEast = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(addressEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AddressEast', () => {
      const addressEast = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(addressEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AddressEast', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AddressEast', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AddressEast', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAddressEastToCollectionIfMissing', () => {
      it('should add a AddressEast to an empty array', () => {
        const addressEast: IAddressEast = sampleWithRequiredData;
        expectedResult = service.addAddressEastToCollectionIfMissing([], addressEast);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(addressEast);
      });

      it('should not add a AddressEast to an array that contains it', () => {
        const addressEast: IAddressEast = sampleWithRequiredData;
        const addressEastCollection: IAddressEast[] = [
          {
            ...addressEast,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAddressEastToCollectionIfMissing(addressEastCollection, addressEast);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AddressEast to an array that doesn't contain it", () => {
        const addressEast: IAddressEast = sampleWithRequiredData;
        const addressEastCollection: IAddressEast[] = [sampleWithPartialData];
        expectedResult = service.addAddressEastToCollectionIfMissing(addressEastCollection, addressEast);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(addressEast);
      });

      it('should add only unique AddressEast to an array', () => {
        const addressEastArray: IAddressEast[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const addressEastCollection: IAddressEast[] = [sampleWithRequiredData];
        expectedResult = service.addAddressEastToCollectionIfMissing(addressEastCollection, ...addressEastArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const addressEast: IAddressEast = sampleWithRequiredData;
        const addressEast2: IAddressEast = sampleWithPartialData;
        expectedResult = service.addAddressEastToCollectionIfMissing([], addressEast, addressEast2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(addressEast);
        expect(expectedResult).toContain(addressEast2);
      });

      it('should accept null and undefined values', () => {
        const addressEast: IAddressEast = sampleWithRequiredData;
        expectedResult = service.addAddressEastToCollectionIfMissing([], null, addressEast, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(addressEast);
      });

      it('should return initial array if no AddressEast is added', () => {
        const addressEastCollection: IAddressEast[] = [sampleWithRequiredData];
        expectedResult = service.addAddressEastToCollectionIfMissing(addressEastCollection, undefined, null);
        expect(expectedResult).toEqual(addressEastCollection);
      });
    });

    describe('compareAddressEast', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAddressEast(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAddressEast(entity1, entity2);
        const compareResult2 = service.compareAddressEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAddressEast(entity1, entity2);
        const compareResult2 = service.compareAddressEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAddressEast(entity1, entity2);
        const compareResult2 = service.compareAddressEast(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
