import { create } from "zustand";
import { persist } from "zustand/middleware";

interface TokenState {
  token: string;
  setToken: (token: string) => void;
}

export const useUserStore = create<TokenState>()(
  persist(
    (set) => ({
      token: "",
      setToken: (token) => {
        set(() => {
          return { token: token };
        });
      }
    }),
    { name: "token" }
  )
);
