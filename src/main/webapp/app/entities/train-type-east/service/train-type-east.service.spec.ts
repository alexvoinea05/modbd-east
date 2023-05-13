import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITrainTypeEast } from '../train-type-east.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../train-type-east.test-samples';

import { TrainTypeEastService } from './train-type-east.service';

const requireRestSample: ITrainTypeEast = {
  ...sampleWithRequiredData,
};

describe('TrainTypeEast Service', () => {
  let service: TrainTypeEastService;
  let httpMock: HttpTestingController;
  let expectedResult: ITrainTypeEast | ITrainTypeEast[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TrainTypeEastService);
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

    it('should create a TrainTypeEast', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const trainTypeEast = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(trainTypeEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TrainTypeEast', () => {
      const trainTypeEast = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(trainTypeEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TrainTypeEast', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TrainTypeEast', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TrainTypeEast', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTrainTypeEastToCollectionIfMissing', () => {
      it('should add a TrainTypeEast to an empty array', () => {
        const trainTypeEast: ITrainTypeEast = sampleWithRequiredData;
        expectedResult = service.addTrainTypeEastToCollectionIfMissing([], trainTypeEast);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainTypeEast);
      });

      it('should not add a TrainTypeEast to an array that contains it', () => {
        const trainTypeEast: ITrainTypeEast = sampleWithRequiredData;
        const trainTypeEastCollection: ITrainTypeEast[] = [
          {
            ...trainTypeEast,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTrainTypeEastToCollectionIfMissing(trainTypeEastCollection, trainTypeEast);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TrainTypeEast to an array that doesn't contain it", () => {
        const trainTypeEast: ITrainTypeEast = sampleWithRequiredData;
        const trainTypeEastCollection: ITrainTypeEast[] = [sampleWithPartialData];
        expectedResult = service.addTrainTypeEastToCollectionIfMissing(trainTypeEastCollection, trainTypeEast);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainTypeEast);
      });

      it('should add only unique TrainTypeEast to an array', () => {
        const trainTypeEastArray: ITrainTypeEast[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const trainTypeEastCollection: ITrainTypeEast[] = [sampleWithRequiredData];
        expectedResult = service.addTrainTypeEastToCollectionIfMissing(trainTypeEastCollection, ...trainTypeEastArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const trainTypeEast: ITrainTypeEast = sampleWithRequiredData;
        const trainTypeEast2: ITrainTypeEast = sampleWithPartialData;
        expectedResult = service.addTrainTypeEastToCollectionIfMissing([], trainTypeEast, trainTypeEast2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainTypeEast);
        expect(expectedResult).toContain(trainTypeEast2);
      });

      it('should accept null and undefined values', () => {
        const trainTypeEast: ITrainTypeEast = sampleWithRequiredData;
        expectedResult = service.addTrainTypeEastToCollectionIfMissing([], null, trainTypeEast, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainTypeEast);
      });

      it('should return initial array if no TrainTypeEast is added', () => {
        const trainTypeEastCollection: ITrainTypeEast[] = [sampleWithRequiredData];
        expectedResult = service.addTrainTypeEastToCollectionIfMissing(trainTypeEastCollection, undefined, null);
        expect(expectedResult).toEqual(trainTypeEastCollection);
      });
    });

    describe('compareTrainTypeEast', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTrainTypeEast(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTrainTypeEast(entity1, entity2);
        const compareResult2 = service.compareTrainTypeEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTrainTypeEast(entity1, entity2);
        const compareResult2 = service.compareTrainTypeEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTrainTypeEast(entity1, entity2);
        const compareResult2 = service.compareTrainTypeEast(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
