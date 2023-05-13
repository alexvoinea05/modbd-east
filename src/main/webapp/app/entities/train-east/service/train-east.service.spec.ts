import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITrainEast } from '../train-east.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../train-east.test-samples';

import { TrainEastService } from './train-east.service';

const requireRestSample: ITrainEast = {
  ...sampleWithRequiredData,
};

describe('TrainEast Service', () => {
  let service: TrainEastService;
  let httpMock: HttpTestingController;
  let expectedResult: ITrainEast | ITrainEast[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TrainEastService);
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

    it('should create a TrainEast', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const trainEast = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(trainEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TrainEast', () => {
      const trainEast = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(trainEast).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TrainEast', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TrainEast', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TrainEast', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTrainEastToCollectionIfMissing', () => {
      it('should add a TrainEast to an empty array', () => {
        const trainEast: ITrainEast = sampleWithRequiredData;
        expectedResult = service.addTrainEastToCollectionIfMissing([], trainEast);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainEast);
      });

      it('should not add a TrainEast to an array that contains it', () => {
        const trainEast: ITrainEast = sampleWithRequiredData;
        const trainEastCollection: ITrainEast[] = [
          {
            ...trainEast,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTrainEastToCollectionIfMissing(trainEastCollection, trainEast);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TrainEast to an array that doesn't contain it", () => {
        const trainEast: ITrainEast = sampleWithRequiredData;
        const trainEastCollection: ITrainEast[] = [sampleWithPartialData];
        expectedResult = service.addTrainEastToCollectionIfMissing(trainEastCollection, trainEast);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainEast);
      });

      it('should add only unique TrainEast to an array', () => {
        const trainEastArray: ITrainEast[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const trainEastCollection: ITrainEast[] = [sampleWithRequiredData];
        expectedResult = service.addTrainEastToCollectionIfMissing(trainEastCollection, ...trainEastArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const trainEast: ITrainEast = sampleWithRequiredData;
        const trainEast2: ITrainEast = sampleWithPartialData;
        expectedResult = service.addTrainEastToCollectionIfMissing([], trainEast, trainEast2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainEast);
        expect(expectedResult).toContain(trainEast2);
      });

      it('should accept null and undefined values', () => {
        const trainEast: ITrainEast = sampleWithRequiredData;
        expectedResult = service.addTrainEastToCollectionIfMissing([], null, trainEast, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainEast);
      });

      it('should return initial array if no TrainEast is added', () => {
        const trainEastCollection: ITrainEast[] = [sampleWithRequiredData];
        expectedResult = service.addTrainEastToCollectionIfMissing(trainEastCollection, undefined, null);
        expect(expectedResult).toEqual(trainEastCollection);
      });
    });

    describe('compareTrainEast', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTrainEast(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTrainEast(entity1, entity2);
        const compareResult2 = service.compareTrainEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTrainEast(entity1, entity2);
        const compareResult2 = service.compareTrainEast(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTrainEast(entity1, entity2);
        const compareResult2 = service.compareTrainEast(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
