import { PostsAPIResponse } from "../../components/blog-feature/BlogTypes";
import { Pagination } from "../../pages/Blog";

export default async function getPosts({
  page = 1,
  limit = 10,
  sortBy = "createdAt",
  sortDesc = "true",
  contains
}: Pagination): Promise<PostsAPIResponse> {
  const titleFilter = contains ? `&contains=${contains}` : "";
  const res = await fetch(
    `${import.meta.env.VITE_BACKEND_HOST}/posts?limit=${limit}&page=${page}&sortBy=${sortBy}&sortDesc=${sortDesc}${titleFilter}`,
    {
      method: "GET", // *GET, POST, PUT, DELETE, etc.
      mode: "cors", // no-cors, *cors, same-origin
      cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
      credentials: "same-origin", // include, *same-origin, omit
      headers: {
        "Content-Type": "application/json"
      },
      redirect: "follow", // manual, *follow, error
      referrerPolicy: "no-referrer"
    }
  );
  if (res.status !== 200) {
    throw new Error("Could not fetch messages");
  }
  const data = await res.json();
  return data;
}
