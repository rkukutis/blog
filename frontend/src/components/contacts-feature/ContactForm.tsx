import { SubmitHandler, useForm } from "react-hook-form";
import FormInlineError from "../FormInlineError";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";

type FormValues = {
  name: string;
  email: string;
  message: string;
};

const invalidFieldStyle = "text-red-500 border-red-500 border-2 rounded-md py-1 px-2 placeholder:text-slate-400";
const defaultFieldStyle = "border-slate-300 border-2 rounded-md py-1 px-2 placeholder:text-slate-400";

function ContactForm() {
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    formState: { errors }
  } = useForm<FormValues>({ mode: "all" });
  const onSubmit: SubmitHandler<FormValues> = () => {
    toast.success("Message sent");
    navigate("/blog");
    // SERVICE TO BACKEND
  };

  return (
    <div className="p-3 rounded-md">
      <form className="flex flex-col space-y-2" onSubmit={handleSubmit(onSubmit)}>
        <section className="flex flex-col">
          <input
            className={defaultFieldStyle + (errors.name && invalidFieldStyle)}
            placeholder="Name"
            {...register("name", {
              required: "Name field can not be empty",
              maxLength: { value: 20, message: "Your name is too long! (>20)" }
            })}
          />
          {errors.name?.message && <FormInlineError message={errors.name.message} />}
        </section>
        <section className="flex flex-col">
          <input
            className={defaultFieldStyle + (errors.email && invalidFieldStyle)}
            placeholder="Email"
            {...register("email", {
              required: "Email field can not be empty",
              maxLength: { value: 30, message: "Your email is too long! (>30)" },
              pattern: { value: /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/, message: "Wrong email format" }
            })}
          />
          {errors.email?.message && <FormInlineError message={errors.email.message} />}
        </section>
        <section className="flex flex-col">
          <textarea
            className={defaultFieldStyle + (errors.message && invalidFieldStyle)}
            placeholder="Message"
            {...register("message", {
              required: "Message field can not be empty",
              maxLength: { value: 200, message: "Your message is too long! (>200)" }
            })}
          />
          {errors.message?.message && <FormInlineError message={errors.message.message} />}
        </section>
        <input
          disabled={errors.email !== undefined || errors.message !== undefined || errors.name !== undefined}
          type="submit"
          name="submit"
          value="Send message"
          className="w-full bg-blue-500 text-slate-50 hover:cursor-pointer rounded-md py-2 disabled:bg-blue-300 disabled:cursor-not-allowed"
        />
      </form>
    </div>
  );
}

export default ContactForm;
