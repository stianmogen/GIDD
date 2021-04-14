export type RequestResponse = {
  detail: string;
};

export type LoginRequestResponse = {
  token: string;
};

export type PaginationResponse<T> = {
  count: number;
  next: number | null;
  previous: number | null;
  results: Array<T>;
};

export type User = {
  user_id: string;
  first_name: string;
  surname: string;
  email: string;
};
export type UserCreate = Pick<User, 'email' | 'first_name' | 'surname'> & {
  password: string;
};

export type ActivityRequired = Partial<Activity> & Pick<Activity, 'title' | 'body'>;

export type Activity = {
  id: number;
  created_at: string;
  updated_at: string;
  title: string;
  body: string;
  image: string;
  image_alt: string;
};
