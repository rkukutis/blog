import { Outlet } from "react-router-dom";
import getTokenFromStorage from "../utils/getTokenFromStorage";

function ProtectedRoute() {
  const token = getTokenFromStorage();
  return (
    <>
      {token ? (
        <Outlet />
      ) : (
        <h1 className=" bg-slate-50 flex flex-col justify-center items-center text-4xl h-[85vh]">🛑 Only logged in users can access this page. 🛑</h1>
      )}
    </>
  );
}

export default ProtectedRoute;
