import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUserTypeEast } from '../user-type-east.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../user-type-east.test-samples';

import { UserTypeEastService } from './user-type-east.service';

const requireRestSample: IUserTypeEast = {
  ...sampleWithRequiredData,
};

describe('UserTypeEast Service', () => {
  let service: UserTypeEastService;
  let httpMock: HttpTestingController;
  let expectedResult: IUserTypeEast | IUserTypeEast[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserTypeEastService);
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

    it('should create a UserTypeEast', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const userTypeEast = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(userTypeEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserTypeEast', () => {
      const userTypeEast = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(userTypeEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UserTypeEast', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UserTypeEast', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a UserTypeEast', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUserTypeEastToCollectionIfMissing', () => {
      it('should add a UserTypeEast to an empty array', () => {
        const userTypeEast: IUserTypeEast = sampleWithRequiredData;
        expectedResult = service.addUserTypeEastToCollectionIfMissing([], userTypeEast);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userTypeEast);
      });

      it('should not add a UserTypeEast to an array that contains it', () => {
        const userTypeEast: IUserTypeEast = sampleWithRequiredData;
        const userTypeEastCollection: IUserTypeEast[] = [
          {
            ...userTypeEast,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUserTypeEastToCollectionIfMissing(userTypeEastCollection, userTypeEast);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserTypeEast to an array that doesn't contain it", () => {
        const userTypeEast: IUserTypeEast = sampleWithRequiredData;
        const userTypeEastCollection: IUserTypeEast[] = [sampleWithPartialData];
        expectedResult = service.addUserTypeEastToCollectionIfMissing(userTypeEastCollection, userTypeEast);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userTypeEast);
      });

      it('should add only unique UserTypeEast to an array', () => {
        const userTypeEastArray: IUserTypeEast[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const userTypeEastCollection: IUserTypeEast[] = [sampleWithRequiredData];
        expectedResult = service.addUserTypeEastToCollectionIfMissing(userTypeEastCollection, ...userTypeEastArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userTypeEast: IUserTypeEast = sampleWithRequiredData;
        const userTypeEast2: IUserTypeEast = sampleWithPartialData;
        expectedResult = service.addUserTypeEastToCollectionIfMissing([], userTypeEast, userTypeEast2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userTypeEast);
        expect(expectedResult).toContain(userTypeEast2);
      });

      it('should accept null and undefined values', () => {
        const userTypeEast: IUserTypeEast = sampleWithRequiredData;
        expectedResult = service.addUserTypeEastToCollectionIfMissing([], null, userTypeEast, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userTypeEast);
      });

      it('should return initial array if no UserTypeEast is added', () => {
        const userTypeEastCollection: IUserTypeEast[] = [sampleWithRequiredData];
        expectedResult = service.addUserTypeEastToCollectionIfMissing(userTypeEastCollection, undefined, null);
        expect(expectedResult).toEqual(userTypeEastCollection);
      });
    });

    describe('compareUserTypeEast', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUserTypeEast(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareUserTypeEast(entity1, entity2);
        const compareResult2 = service.compareUserTypeEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareUserTypeEast(entity1, entity2);
        const compareResult2 = service.compareUserTypeEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareUserTypeEast(entity1, entity2);
        const compareResult2 = service.compareUserTypeEast(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
