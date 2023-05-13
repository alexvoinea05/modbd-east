export interface IJourneyStatusEast {
  id: number;
  code?: string | null;
  description?: string | null;
}

export type NewJourneyStatusEast = Omit<IJourneyStatusEast, 'id'> & { id: null };
