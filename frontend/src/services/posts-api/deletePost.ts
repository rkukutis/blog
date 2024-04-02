import getTokenFromStorage from "../../utils/getTokenFromStorage";

export async function deletePost(postId: string) {
  const res = await fetch(`${import.meta.env.VITE_BACKEND_HOST}/posts/${postId}`, {
    method: "DELETE", // *GET, POST, PUT, DELETE, etc.
    mode: "cors", // no-cors, *cors, same-origin
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${getTokenFromStorage()}`
    }
  });
  if (res.status !== 204) {
    throw new Error("Could not delete message");
  }
}
