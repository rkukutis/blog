import { NavLink } from "react-router-dom";

interface ButtonActive {
  isActive: boolean;
  isPending: boolean;
}

const style = ({ isActive }: ButtonActive) => (isActive ? "text-slate-50" : "text-slate-800");

export default function NavigationBar() {
  return (
    <nav className="flex justify-center space-x-12 text-slate-800 text-[1.4rem]">
      <NavLink className={style} to="/about">
        About
      </NavLink>
      <NavLink className={style} to="/blog">
        Blog
      </NavLink>
      <NavLink className={style} to="/contacts">
        Contacts
      </NavLink>
    </nav>
  );
}
