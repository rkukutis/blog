import { Pagination } from "../../pages/Blog";

export interface PostTheme {
  id: string;
  name: string;
  colorHex: string;
  createdAt: string;
}

export interface ThemesAPIResponse {
  content: PostTheme[];
  last: boolean;
  totalPages: number;
  totalElements: number;
  first: boolean;
  size: number;
  number: number;
  numberOfElements: number;
  empty: boolean;
}

export default async function getThemes({ page = 0, limit = 10, sortBy = "createdAt", sortDesc = "true" }: Pagination): Promise<ThemesAPIResponse> {
  const res = await fetch(`${import.meta.env.VITE_BACKEND_HOST}/themes?limit=${limit}&page=${page}&sortBy=${sortBy}&sortDesc=${sortDesc}`, {
    method: "GET",
    mode: "cors"
  });
  if (res.status !== 200) {
    throw new Error("Could not fetch themes");
  }
  const data = await res.json();
  return data;
}
