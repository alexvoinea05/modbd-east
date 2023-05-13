import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILicenseEast } from '../license-east.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../license-east.test-samples';

import { LicenseEastService } from './license-east.service';

const requireRestSample: ILicenseEast = {
  ...sampleWithRequiredData,
};

describe('LicenseEast Service', () => {
  let service: LicenseEastService;
  let httpMock: HttpTestingController;
  let expectedResult: ILicenseEast | ILicenseEast[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LicenseEastService);
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

    it('should create a LicenseEast', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const licenseEast = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(licenseEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LicenseEast', () => {
      const licenseEast = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(licenseEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LicenseEast', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LicenseEast', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LicenseEast', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLicenseEastToCollectionIfMissing', () => {
      it('should add a LicenseEast to an empty array', () => {
        const licenseEast: ILicenseEast = sampleWithRequiredData;
        expectedResult = service.addLicenseEastToCollectionIfMissing([], licenseEast);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(licenseEast);
      });

      it('should not add a LicenseEast to an array that contains it', () => {
        const licenseEast: ILicenseEast = sampleWithRequiredData;
        const licenseEastCollection: ILicenseEast[] = [
          {
            ...licenseEast,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLicenseEastToCollectionIfMissing(licenseEastCollection, licenseEast);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LicenseEast to an array that doesn't contain it", () => {
        const licenseEast: ILicenseEast = sampleWithRequiredData;
        const licenseEastCollection: ILicenseEast[] = [sampleWithPartialData];
        expectedResult = service.addLicenseEastToCollectionIfMissing(licenseEastCollection, licenseEast);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(licenseEast);
      });

      it('should add only unique LicenseEast to an array', () => {
        const licenseEastArray: ILicenseEast[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const licenseEastCollection: ILicenseEast[] = [sampleWithRequiredData];
        expectedResult = service.addLicenseEastToCollectionIfMissing(licenseEastCollection, ...licenseEastArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const licenseEast: ILicenseEast = sampleWithRequiredData;
        const licenseEast2: ILicenseEast = sampleWithPartialData;
        expectedResult = service.addLicenseEastToCollectionIfMissing([], licenseEast, licenseEast2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(licenseEast);
        expect(expectedResult).toContain(licenseEast2);
      });

      it('should accept null and undefined values', () => {
        const licenseEast: ILicenseEast = sampleWithRequiredData;
        expectedResult = service.addLicenseEastToCollectionIfMissing([], null, licenseEast, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(licenseEast);
      });

      it('should return initial array if no LicenseEast is added', () => {
        const licenseEastCollection: ILicenseEast[] = [sampleWithRequiredData];
        expectedResult = service.addLicenseEastToCollectionIfMissing(licenseEastCollection, undefined, null);
        expect(expectedResult).toEqual(licenseEastCollection);
      });
    });

    describe('compareLicenseEast', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLicenseEast(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLicenseEast(entity1, entity2);
        const compareResult2 = service.compareLicenseEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLicenseEast(entity1, entity2);
        const compareResult2 = service.compareLicenseEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLicenseEast(entity1, entity2);
        const compareResult2 = service.compareLicenseEast(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
