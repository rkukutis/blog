import { useNavigate } from "react-router-dom";
import sadCat from "../assets/sad-cat.jpg";

export default function PageNotFound() {
  const navigate = useNavigate();

  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <div className="bg-black p-5">
        <div className="flex flex-col items-center justify-center border-2 border-white p-5">
          <img src={sadCat} />
          <h1 className="text font-bold text-8xl py-2 text-slate-50 underline">404</h1>
          <h3 className="text-slate-50 py-3">
            There's nothing here,{" "}
            <span onClick={() => navigate(-1)} className="underline hover:cursor-pointer">
              go back
            </span>
          </h3>
        </div>
      </div>
    </div>
  );
}
