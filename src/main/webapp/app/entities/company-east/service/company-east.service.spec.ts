import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICompanyEast } from '../company-east.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../company-east.test-samples';

import { CompanyEastService } from './company-east.service';

const requireRestSample: ICompanyEast = {
  ...sampleWithRequiredData,
};

describe('CompanyEast Service', () => {
  let service: CompanyEastService;
  let httpMock: HttpTestingController;
  let expectedResult: ICompanyEast | ICompanyEast[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CompanyEastService);
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

    it('should create a CompanyEast', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const companyEast = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(companyEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CompanyEast', () => {
      const companyEast = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(companyEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CompanyEast', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CompanyEast', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CompanyEast', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCompanyEastToCollectionIfMissing', () => {
      it('should add a CompanyEast to an empty array', () => {
        const companyEast: ICompanyEast = sampleWithRequiredData;
        expectedResult = service.addCompanyEastToCollectionIfMissing([], companyEast);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(companyEast);
      });

      it('should not add a CompanyEast to an array that contains it', () => {
        const companyEast: ICompanyEast = sampleWithRequiredData;
        const companyEastCollection: ICompanyEast[] = [
          {
            ...companyEast,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCompanyEastToCollectionIfMissing(companyEastCollection, companyEast);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CompanyEast to an array that doesn't contain it", () => {
        const companyEast: ICompanyEast = sampleWithRequiredData;
        const companyEastCollection: ICompanyEast[] = [sampleWithPartialData];
        expectedResult = service.addCompanyEastToCollectionIfMissing(companyEastCollection, companyEast);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(companyEast);
      });

      it('should add only unique CompanyEast to an array', () => {
        const companyEastArray: ICompanyEast[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const companyEastCollection: ICompanyEast[] = [sampleWithRequiredData];
        expectedResult = service.addCompanyEastToCollectionIfMissing(companyEastCollection, ...companyEastArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const companyEast: ICompanyEast = sampleWithRequiredData;
        const companyEast2: ICompanyEast = sampleWithPartialData;
        expectedResult = service.addCompanyEastToCollectionIfMissing([], companyEast, companyEast2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(companyEast);
        expect(expectedResult).toContain(companyEast2);
      });

      it('should accept null and undefined values', () => {
        const companyEast: ICompanyEast = sampleWithRequiredData;
        expectedResult = service.addCompanyEastToCollectionIfMissing([], null, companyEast, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(companyEast);
      });

      it('should return initial array if no CompanyEast is added', () => {
        const companyEastCollection: ICompanyEast[] = [sampleWithRequiredData];
        expectedResult = service.addCompanyEastToCollectionIfMissing(companyEastCollection, undefined, null);
        expect(expectedResult).toEqual(companyEastCollection);
      });
    });

    describe('compareCompanyEast', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCompanyEast(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCompanyEast(entity1, entity2);
        const compareResult2 = service.compareCompanyEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCompanyEast(entity1, entity2);
        const compareResult2 = service.compareCompanyEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCompanyEast(entity1, entity2);
        const compareResult2 = service.compareCompanyEast(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
