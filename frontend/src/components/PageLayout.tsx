import { Outlet, useNavigate } from "react-router-dom";
import { useUserStore } from "../stores/tokenStore";
import Button from "./Button";
import NavigationBar from "./NavigationBar";
import githubLogo from "../assets/github-logo.svg";

export default function PageLayout() {
  const navigate = useNavigate();
  const { token, setToken } = useUserStore();

  function logout() {
    setToken("");
    navigate("/about");
  }

  return (
    <>
      <header className="py-9 bg-blue-400 flex justify-center px-4 items-center h-[10vh]">
        <NavigationBar />
        {token && (
          <div className="text absolute right-5">
            <Button onclick={logout}>Log out</Button>
          </div>
        )}
      </header>
      <main className="min-h-[85vh]">
        <Outlet />
      </main>
      <footer className="bg-slate-800 py-1 text-slate-100 flex flex-col items-center justify-center h-[5vh]">
        <a className="flex items-center space-x-3 text-sm" href="https://github.com/rkukutis">
          <img className="h-4 inline-block" src={githubLogo} />
          <p>Check out my other stuff</p>
        </a>
      </footer>
    </>
  );
}
