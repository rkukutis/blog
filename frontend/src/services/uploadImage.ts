import getTokenFromStorage from "../utils/getTokenFromStorage";

export default async function (image: File): Promise<string> {
  const formData = new FormData();
  formData.append("image", image);
  const res = await fetch(`${import.meta.env.VITE_BACKEND_HOST}/uploads/images`, {
    method: "post",
    headers: { Authorization: `Bearer ${getTokenFromStorage()}` },
    body: formData
  });
  if (res.status !== 201) {
    throw new Error();
  }
  const imagePath: string = await res.text();
  return imagePath;
}
