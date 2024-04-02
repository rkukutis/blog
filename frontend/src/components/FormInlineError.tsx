export default function FormInlineError({ message }: { message: string }) {
  return <span className="bg-red-100 w-full p-1 my-2 text-red-500 text-center text-sm rounded-md block">{message}</span>;
}
