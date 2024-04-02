import { Pagination } from "../../pages/Blog";

export interface PostProps {
  uuid: string;
  title: string;
  subtitle: string;
  themes: string[];
  body: string;
  thumbnail: string;
  createdAt: string;
  modifiedAt: string;
}

export interface PaginationSettingsProps {
  setPagination: (pagination: Pagination) => void;
  pagination: Pagination;
  last: boolean;
  first: boolean;
  totalPages: number;
  totalElements: number;
}

export interface PostFormProps {
  closeForm: () => void;
  initialFieldValues?: { body: string; thumbnail: string; themes: string[]; subtitle: string; title: string };
  method?: string;
  postId?: string;
}

export interface PostFormsFields {
  title: string;
  subtitle: string;
  themes: string[];
  body: string;
  thumbnail: FileList;
}

export interface PostsAPIResponse {
  content: PostProps[];
  last: boolean;
  totalPages: number;
  totalElements: number;
  first: boolean;
  size: number;
  number: number;
  numberOfElements: number;
  empty: boolean;
}

export interface PostTheme {
  name: string;
  color: string;
  emoji: string;
}
