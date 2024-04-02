interface LoginDetails {
  username: string;
  password: string;
}

interface AuthResponse {
  token: string;
}

export default async function ({ username, password }: LoginDetails): Promise<string> {
  const res = await fetch(`${import.meta.env.VITE_BACKEND_HOST}/auth/login`, {
    method: "POST", // *GET, POST, PUT, DELETE, etc.
    mode: "cors", // no-cors, *cors, same-origin
    cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
    headers: {
      "Content-Type": "application/json"
    },
    redirect: "follow", // manual, *follow, error
    referrerPolicy: "no-referrer",
    body: JSON.stringify({ username, password })
  });
  if (res.status === 200) {
    const jwt: AuthResponse = await res.json();
    return jwt.token;
  } else if (res.status === 403) {
    throw new Error("Wrong username or password");
  } else {
    throw new Error("Could not log in due to server error. Try again later");
  }
}
