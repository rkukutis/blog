export default function (): string | null {
  const data = localStorage.getItem("token");
  if (!data) return null;
  const {
    state: { token }
  } = JSON.parse(data);
  return token;
}
