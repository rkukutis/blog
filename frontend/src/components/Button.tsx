interface ButtonProps {
  children: string;
  type?: string;
  extraStyle?: string;
  onclick?: () => void;
  disabled?: boolean;
}

const styles = {
  normal: "bg-blue-500 px-5 py-2 rounded-md text-slate-100 hover:bg-blue-300",
  danger: "bg-red-500 px-5 py-2 rounded-md text-red-100 hover:bg-red-300"
};
const modifiers = {
  disabled: "bg-slate-300 px-5 py-2 rounded-md text-slate-700 hover:bg-slate-300 hover:cursor-not-allowed"
};

export default function Button({ children, type = styles.normal, onclick, extraStyle, disabled }: ButtonProps) {
  let style: string = styles.normal;
  if (type === "danger") {
    style = styles.danger;
  }

  return (
    <button disabled={disabled} onClick={onclick} className={style + ` ${extraStyle} ${disabled && modifiers.disabled}`}>
      {children}
    </button>
  );
}
