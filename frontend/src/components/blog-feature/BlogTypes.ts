import { Pagination } from "../../pages/Blog";
import { PostTheme } from "../../services/themes-api/getThemes";

export interface PostProps {
  uuid: string;
  title: string;
  subtitle: string;
  themes: PostTheme[];
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
  initialFieldValues?: { body: string; thumbnail: string; themes: PostTheme[]; subtitle: string; title: string };
  method?: string;
  postId?: string;
}

export interface PostFormsFields {
  title: string;
  subtitle: string;
  themes: PostTheme[];
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
