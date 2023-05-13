export interface IAddressEast {
  id: number;
  streetNumber?: string | null;
  street?: string | null;
  zipcode?: string | null;
  cityId?: number | null;
}

export type NewAddressEast = Omit<IAddressEast, 'id'> & { id: null };
