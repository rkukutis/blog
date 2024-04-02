import { Editor } from "@tiptap/react";
import uploadImage from "../../services/uploadImage";
import imageIcon from "../../assets/editor-icons/image_FILL0_wght400_GRAD0_opsz24.svg";

function ImageUploadButton({ editor }: { editor: Editor }) {
  // TODO: prevent image upload if post is cancelled or image is removed
  const handleFileChange = async function (e: React.ChangeEvent<HTMLInputElement>) {
    e.preventDefault();
    if (e.target.files && e.target.files[0]) {
      const file = e.target.files[0];
      const imagePath = await uploadImage(file);
      const alt = imagePath.split("/")[imagePath.split("/").length - 1];
      editor.chain().focus().setImage({ src: imagePath, alt: alt, title: alt }).run();
    }
  };

  return (
    <div>
      <label htmlFor="file" className="hover:cursor-pointer">
        <img src={imageIcon} />
      </label>
      <input id="file" type="file" className="hidden" onChange={handleFileChange} />
    </div>
  );
}

export default ImageUploadButton;
