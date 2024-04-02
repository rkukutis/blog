import getTokenFromStorage from "../../utils/getTokenFromStorage";

interface Post {
  title: string;
  subtitle: string;
  themes: string[];
  body: string;
  thumbnail: string;
}

export default async function createPost(post: Post) {
  const res = await fetch(`${import.meta.env.VITE_BACKEND_HOST}/posts`, {
    method: "POST",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${getTokenFromStorage()}`
    },
    body: JSON.stringify(post)
  });
  if (res.status !== 201) {
    throw new Error("Could not create new Post");
  }
}
