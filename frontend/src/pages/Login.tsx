import { SubmitHandler, useForm } from "react-hook-form";
import login from "../services/login";
import { useUserStore } from "../stores/tokenStore";
import { useNavigate } from "react-router-dom";
import toast from "react-hot-toast";

type Inputs = { username: string; password: string };

interface ErrorInput {
  message: string;
}

function ErrorLabel({ message }: ErrorInput) {
  return <span className="bg-red-100 w-full mt-2 p-1 text-red-500 text-center">{message}</span>;
}

export default function Login() {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset
  } = useForm<Inputs>();

  const { setToken } = useUserStore();
  const navigate = useNavigate();

  const onSubmit: SubmitHandler<Inputs> = async (data) => {
    let loginResponse;
    try {
      loginResponse = await login({ username: data.username, password: data.password });
      toast.success("Login successful");
      setToken(loginResponse);
      navigate("/blog");
    } catch (e) {
      toast.error((e as Error).message);
    } finally {
      reset();
    }
  };

  return (
    <div className="flex flex-col items-center justify-center h-[85vh]">
      <form className="bg-slate-200 py-6 px-4 flex flex-col space-y-4 rounded-md w-4/5 lg:w-1/5" onSubmit={handleSubmit(onSubmit)}>
        <div className="flex flex-col space-y-1">
          <label>Username</label>
          <input className="w-full p-1" {...register("username", { required: true })} />
          {errors.username && <ErrorLabel message="username is required" />}
        </div>
        <div className="flex flex-col space-y-1">
          <label>Password</label>
          <input type="password" className="w-full p-1" {...register("password", { required: true })} />
          {errors.password && <ErrorLabel message="password is required" />}
        </div>
        <input value="Log in" className="bg-blue-500 py-1 w-full hover:curs rounded-md text-slate-100 hover:cursor-pointer" type="submit"></input>
      </form>
    </div>
  );
}
