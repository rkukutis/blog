import { ChangeEvent, Dispatch, SetStateAction } from "react";
import closeIcon from "../../assets/button-icons/close_FILL0_wght400_GRAD0_opsz24.svg";

function ThemeList({ themes, setPostThemes }: { themes: string[]; setPostThemes: Dispatch<SetStateAction<string[]>> }) {
  function handleThemeChange(val: string) {
    if (themes.includes(val)) return;
    setPostThemes((list) => [...list, val]);
  }

  function handleRemoveTheme(val: string) {
    setPostThemes((list) => list.filter((theme) => theme !== val));
  }

  return (
    <div className="flex items-center">
      <h1>Add post themes</h1>
      <select onChange={(e: ChangeEvent<HTMLSelectElement>) => handleThemeChange(e.target.value)}>
        <option value="job">Job</option>
        <option value="technology">Technology</option>
        <option value="biology">Biology</option>
        <option value="programming">Programming</option>
        <option value="diy">DIY</option>
        <option value="printing_3d">3D Printing</option>
        <option value="photography">Photography</option>
        <option value="hobby">Hobby</option>
        <option value="cooking">Cooking</option>
        <option value="travel">Travel</option>
        <option value="electronics">Electronics</option>
        <option value="testing">Testing</option>
        <option value="films">Films</option>
        <option value="memes">Memes</option>
      </select>
      <div className="flex space-x-1 flex-wrap space-y-1 items-center">
        {themes.map((v) => (
          <div className="flex space-x-1 items-center p-2 bg-blue-500 rounded-md" key={v}>
            <p className="text-slate-50">{v.charAt(0).toUpperCase() + v.slice(1)}</p>
            <button onClick={() => handleRemoveTheme(v)}>
              <img src={closeIcon} alt="remove-theme" />
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default ThemeList;
