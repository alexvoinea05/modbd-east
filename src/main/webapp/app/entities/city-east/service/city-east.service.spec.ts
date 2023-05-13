import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICityEast } from '../city-east.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../city-east.test-samples';

import { CityEastService } from './city-east.service';

const requireRestSample: ICityEast = {
  ...sampleWithRequiredData,
};

describe('CityEast Service', () => {
  let service: CityEastService;
  let httpMock: HttpTestingController;
  let expectedResult: ICityEast | ICityEast[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CityEastService);
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

    it('should create a CityEast', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const cityEast = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cityEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CityEast', () => {
      const cityEast = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cityEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CityEast', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CityEast', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CityEast', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCityEastToCollectionIfMissing', () => {
      it('should add a CityEast to an empty array', () => {
        const cityEast: ICityEast = sampleWithRequiredData;
        expectedResult = service.addCityEastToCollectionIfMissing([], cityEast);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cityEast);
      });

      it('should not add a CityEast to an array that contains it', () => {
        const cityEast: ICityEast = sampleWithRequiredData;
        const cityEastCollection: ICityEast[] = [
          {
            ...cityEast,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCityEastToCollectionIfMissing(cityEastCollection, cityEast);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CityEast to an array that doesn't contain it", () => {
        const cityEast: ICityEast = sampleWithRequiredData;
        const cityEastCollection: ICityEast[] = [sampleWithPartialData];
        expectedResult = service.addCityEastToCollectionIfMissing(cityEastCollection, cityEast);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cityEast);
      });

      it('should add only unique CityEast to an array', () => {
        const cityEastArray: ICityEast[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cityEastCollection: ICityEast[] = [sampleWithRequiredData];
        expectedResult = service.addCityEastToCollectionIfMissing(cityEastCollection, ...cityEastArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cityEast: ICityEast = sampleWithRequiredData;
        const cityEast2: ICityEast = sampleWithPartialData;
        expectedResult = service.addCityEastToCollectionIfMissing([], cityEast, cityEast2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cityEast);
        expect(expectedResult).toContain(cityEast2);
      });

      it('should accept null and undefined values', () => {
        const cityEast: ICityEast = sampleWithRequiredData;
        expectedResult = service.addCityEastToCollectionIfMissing([], null, cityEast, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cityEast);
      });

      it('should return initial array if no CityEast is added', () => {
        const cityEastCollection: ICityEast[] = [sampleWithRequiredData];
        expectedResult = service.addCityEastToCollectionIfMissing(cityEastCollection, undefined, null);
        expect(expectedResult).toEqual(cityEastCollection);
      });
    });

    describe('compareCityEast', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCityEast(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCityEast(entity1, entity2);
        const compareResult2 = service.compareCityEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCityEast(entity1, entity2);
        const compareResult2 = service.compareCityEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCityEast(entity1, entity2);
        const compareResult2 = service.compareCityEast(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
