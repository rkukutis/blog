import Image from "image-js";

const NEW_IMAGE_HEIGHT = 250;

export async function resizeThumbnail(file: File | null): Promise<string> {
  if (!file) return "";
  const buffer: ArrayBuffer = await file.arrayBuffer();
  const image: Image = await Image.load(buffer);
  return image.resize({ height: NEW_IMAGE_HEIGHT, preserveAspectRatio: true }).toBase64();
}
