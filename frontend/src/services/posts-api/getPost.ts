import { PostProps } from "../../components/blog-feature/BlogTypes";

export default async function getPost(postUUID: string | undefined): Promise<PostProps | null> {
  if (!postUUID) return null;
  const res = await fetch(`${import.meta.env.VITE_BACKEND_HOST}/posts/${postUUID}`, {
    method: "GET", // *GET, POST, PUT, DELETE, etc.
    mode: "cors", // no-cors, *cors, same-origin
    cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
    credentials: "same-origin", // include, *same-origin, omit
    headers: {
      "Content-Type": "application/json"
    },
    redirect: "follow", // manual, *follow, error
    referrerPolicy: "no-referrer"
  });
  if (res.status !== 200) {
    throw new Error(`Could not fetch message ID:${postUUID}`);
  }
  const data = await res.json();
  return data;
}
