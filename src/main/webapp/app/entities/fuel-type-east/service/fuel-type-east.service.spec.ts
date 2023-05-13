import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFuelTypeEast } from '../fuel-type-east.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../fuel-type-east.test-samples';

import { FuelTypeEastService } from './fuel-type-east.service';

const requireRestSample: IFuelTypeEast = {
  ...sampleWithRequiredData,
};

describe('FuelTypeEast Service', () => {
  let service: FuelTypeEastService;
  let httpMock: HttpTestingController;
  let expectedResult: IFuelTypeEast | IFuelTypeEast[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FuelTypeEastService);
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

    it('should create a FuelTypeEast', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const fuelTypeEast = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fuelTypeEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FuelTypeEast', () => {
      const fuelTypeEast = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fuelTypeEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FuelTypeEast', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FuelTypeEast', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FuelTypeEast', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFuelTypeEastToCollectionIfMissing', () => {
      it('should add a FuelTypeEast to an empty array', () => {
        const fuelTypeEast: IFuelTypeEast = sampleWithRequiredData;
        expectedResult = service.addFuelTypeEastToCollectionIfMissing([], fuelTypeEast);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fuelTypeEast);
      });

      it('should not add a FuelTypeEast to an array that contains it', () => {
        const fuelTypeEast: IFuelTypeEast = sampleWithRequiredData;
        const fuelTypeEastCollection: IFuelTypeEast[] = [
          {
            ...fuelTypeEast,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFuelTypeEastToCollectionIfMissing(fuelTypeEastCollection, fuelTypeEast);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FuelTypeEast to an array that doesn't contain it", () => {
        const fuelTypeEast: IFuelTypeEast = sampleWithRequiredData;
        const fuelTypeEastCollection: IFuelTypeEast[] = [sampleWithPartialData];
        expectedResult = service.addFuelTypeEastToCollectionIfMissing(fuelTypeEastCollection, fuelTypeEast);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fuelTypeEast);
      });

      it('should add only unique FuelTypeEast to an array', () => {
        const fuelTypeEastArray: IFuelTypeEast[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fuelTypeEastCollection: IFuelTypeEast[] = [sampleWithRequiredData];
        expectedResult = service.addFuelTypeEastToCollectionIfMissing(fuelTypeEastCollection, ...fuelTypeEastArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fuelTypeEast: IFuelTypeEast = sampleWithRequiredData;
        const fuelTypeEast2: IFuelTypeEast = sampleWithPartialData;
        expectedResult = service.addFuelTypeEastToCollectionIfMissing([], fuelTypeEast, fuelTypeEast2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fuelTypeEast);
        expect(expectedResult).toContain(fuelTypeEast2);
      });

      it('should accept null and undefined values', () => {
        const fuelTypeEast: IFuelTypeEast = sampleWithRequiredData;
        expectedResult = service.addFuelTypeEastToCollectionIfMissing([], null, fuelTypeEast, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fuelTypeEast);
      });

      it('should return initial array if no FuelTypeEast is added', () => {
        const fuelTypeEastCollection: IFuelTypeEast[] = [sampleWithRequiredData];
        expectedResult = service.addFuelTypeEastToCollectionIfMissing(fuelTypeEastCollection, undefined, null);
        expect(expectedResult).toEqual(fuelTypeEastCollection);
      });
    });

    describe('compareFuelTypeEast', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFuelTypeEast(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFuelTypeEast(entity1, entity2);
        const compareResult2 = service.compareFuelTypeEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFuelTypeEast(entity1, entity2);
        const compareResult2 = service.compareFuelTypeEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFuelTypeEast(entity1, entity2);
        const compareResult2 = service.compareFuelTypeEast(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
