import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAppUserEast } from '../app-user-east.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../app-user-east.test-samples';

import { AppUserEastService } from './app-user-east.service';

const requireRestSample: IAppUserEast = {
  ...sampleWithRequiredData,
};

describe('AppUserEast Service', () => {
  let service: AppUserEastService;
  let httpMock: HttpTestingController;
  let expectedResult: IAppUserEast | IAppUserEast[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AppUserEastService);
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

    it('should create a AppUserEast', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const appUserEast = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(appUserEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AppUserEast', () => {
      const appUserEast = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(appUserEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AppUserEast', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AppUserEast', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AppUserEast', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAppUserEastToCollectionIfMissing', () => {
      it('should add a AppUserEast to an empty array', () => {
        const appUserEast: IAppUserEast = sampleWithRequiredData;
        expectedResult = service.addAppUserEastToCollectionIfMissing([], appUserEast);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(appUserEast);
      });

      it('should not add a AppUserEast to an array that contains it', () => {
        const appUserEast: IAppUserEast = sampleWithRequiredData;
        const appUserEastCollection: IAppUserEast[] = [
          {
            ...appUserEast,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAppUserEastToCollectionIfMissing(appUserEastCollection, appUserEast);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AppUserEast to an array that doesn't contain it", () => {
        const appUserEast: IAppUserEast = sampleWithRequiredData;
        const appUserEastCollection: IAppUserEast[] = [sampleWithPartialData];
        expectedResult = service.addAppUserEastToCollectionIfMissing(appUserEastCollection, appUserEast);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(appUserEast);
      });

      it('should add only unique AppUserEast to an array', () => {
        const appUserEastArray: IAppUserEast[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const appUserEastCollection: IAppUserEast[] = [sampleWithRequiredData];
        expectedResult = service.addAppUserEastToCollectionIfMissing(appUserEastCollection, ...appUserEastArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const appUserEast: IAppUserEast = sampleWithRequiredData;
        const appUserEast2: IAppUserEast = sampleWithPartialData;
        expectedResult = service.addAppUserEastToCollectionIfMissing([], appUserEast, appUserEast2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(appUserEast);
        expect(expectedResult).toContain(appUserEast2);
      });

      it('should accept null and undefined values', () => {
        const appUserEast: IAppUserEast = sampleWithRequiredData;
        expectedResult = service.addAppUserEastToCollectionIfMissing([], null, appUserEast, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(appUserEast);
      });

      it('should return initial array if no AppUserEast is added', () => {
        const appUserEastCollection: IAppUserEast[] = [sampleWithRequiredData];
        expectedResult = service.addAppUserEastToCollectionIfMissing(appUserEastCollection, undefined, null);
        expect(expectedResult).toEqual(appUserEastCollection);
      });
    });

    describe('compareAppUserEast', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAppUserEast(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAppUserEast(entity1, entity2);
        const compareResult2 = service.compareAppUserEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAppUserEast(entity1, entity2);
        const compareResult2 = service.compareAppUserEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAppUserEast(entity1, entity2);
        const compareResult2 = service.compareAppUserEast(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
