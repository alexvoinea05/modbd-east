import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICompanyLicenseEast } from '../company-license-east.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../company-license-east.test-samples';

import { CompanyLicenseEastService } from './company-license-east.service';

const requireRestSample: ICompanyLicenseEast = {
  ...sampleWithRequiredData,
};

describe('CompanyLicenseEast Service', () => {
  let service: CompanyLicenseEastService;
  let httpMock: HttpTestingController;
  let expectedResult: ICompanyLicenseEast | ICompanyLicenseEast[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CompanyLicenseEastService);
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

    it('should create a CompanyLicenseEast', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const companyLicenseEast = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(companyLicenseEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CompanyLicenseEast', () => {
      const companyLicenseEast = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(companyLicenseEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CompanyLicenseEast', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CompanyLicenseEast', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CompanyLicenseEast', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCompanyLicenseEastToCollectionIfMissing', () => {
      it('should add a CompanyLicenseEast to an empty array', () => {
        const companyLicenseEast: ICompanyLicenseEast = sampleWithRequiredData;
        expectedResult = service.addCompanyLicenseEastToCollectionIfMissing([], companyLicenseEast);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(companyLicenseEast);
      });

      it('should not add a CompanyLicenseEast to an array that contains it', () => {
        const companyLicenseEast: ICompanyLicenseEast = sampleWithRequiredData;
        const companyLicenseEastCollection: ICompanyLicenseEast[] = [
          {
            ...companyLicenseEast,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCompanyLicenseEastToCollectionIfMissing(companyLicenseEastCollection, companyLicenseEast);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CompanyLicenseEast to an array that doesn't contain it", () => {
        const companyLicenseEast: ICompanyLicenseEast = sampleWithRequiredData;
        const companyLicenseEastCollection: ICompanyLicenseEast[] = [sampleWithPartialData];
        expectedResult = service.addCompanyLicenseEastToCollectionIfMissing(companyLicenseEastCollection, companyLicenseEast);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(companyLicenseEast);
      });

      it('should add only unique CompanyLicenseEast to an array', () => {
        const companyLicenseEastArray: ICompanyLicenseEast[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const companyLicenseEastCollection: ICompanyLicenseEast[] = [sampleWithRequiredData];
        expectedResult = service.addCompanyLicenseEastToCollectionIfMissing(companyLicenseEastCollection, ...companyLicenseEastArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const companyLicenseEast: ICompanyLicenseEast = sampleWithRequiredData;
        const companyLicenseEast2: ICompanyLicenseEast = sampleWithPartialData;
        expectedResult = service.addCompanyLicenseEastToCollectionIfMissing([], companyLicenseEast, companyLicenseEast2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(companyLicenseEast);
        expect(expectedResult).toContain(companyLicenseEast2);
      });

      it('should accept null and undefined values', () => {
        const companyLicenseEast: ICompanyLicenseEast = sampleWithRequiredData;
        expectedResult = service.addCompanyLicenseEastToCollectionIfMissing([], null, companyLicenseEast, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(companyLicenseEast);
      });

      it('should return initial array if no CompanyLicenseEast is added', () => {
        const companyLicenseEastCollection: ICompanyLicenseEast[] = [sampleWithRequiredData];
        expectedResult = service.addCompanyLicenseEastToCollectionIfMissing(companyLicenseEastCollection, undefined, null);
        expect(expectedResult).toEqual(companyLicenseEastCollection);
      });
    });

    describe('compareCompanyLicenseEast', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCompanyLicenseEast(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCompanyLicenseEast(entity1, entity2);
        const compareResult2 = service.compareCompanyLicenseEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCompanyLicenseEast(entity1, entity2);
        const compareResult2 = service.compareCompanyLicenseEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCompanyLicenseEast(entity1, entity2);
        const compareResult2 = service.compareCompanyLicenseEast(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
