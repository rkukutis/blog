import { Editor } from "@tiptap/react";
import boldIcon from "../../assets/editor-icons/format_bold_FILL0_wght400_GRAD0_opsz24.svg";
import italicIcon from "../../assets/editor-icons/format_italic_FILL0_wght400_GRAD0_opsz24.svg";
import strikeIcon from "../../assets/editor-icons/strikethrough_s_FILL0_wght400_GRAD0_opsz24.svg";
import underlineIcon from "../../assets/editor-icons/format_underlined_FILL0_wght400_GRAD0_opsz24.svg";
import codeIcon from "../../assets/editor-icons/code_FILL0_wght400_GRAD0_opsz24.svg";
import bulletListIcon from "../../assets/editor-icons/list_FILL0_wght400_GRAD0_opsz24.svg";
import numberedListIcon from "../../assets/editor-icons/format_list_numbered_rtl_FILL0_wght400_GRAD0_opsz24.svg";
import quoteIcon from "../../assets/editor-icons/format_quote_FILL0_wght400_GRAD0_opsz24.svg";
import horizontalRuleIcon from "../../assets/editor-icons/horizontal_rule_FILL0_wght400_GRAD0_opsz24.svg";
import undoIcon from "../../assets/editor-icons/undo_FILL0_wght400_GRAD0_opsz24.svg";
import redoIcon from "../../assets/editor-icons/redo_FILL0_wght400_GRAD0_opsz24.svg";
import ImageUploadButton from "./ImageUploadButton";

export default function MenuBar({ editor }: { editor: Editor | null }) {
  type Level = 1 | 2 | 3 | 4 | 5 | 6;

  function numberToLevel(num: number): Level {
    if (num > 0 && num < 7) {
      return num as Level;
    } else {
      return 1;
    }
  }
  const baseStyle = "p-2";
  const selectedStyle = "p-2 bg-blue-400 rounded-md";

  if (!editor) {
    return null;
  }

  function setHeaderLevel(value: Level) {
    if (!editor) return;
    editor.chain().focus().setHeading({ level: value }).run();
  }

  return (
    <div className="flex flex-wrap items-center justify-center border-2 rounded-md w-full">
      <button
        type="button"
        onClick={() => editor.chain().focus().toggleBold().run()}
        disabled={!editor.can().chain().focus().toggleBold().run()}
        className={editor.isActive("bold") ? selectedStyle : baseStyle}
      >
        <img src={boldIcon} />
      </button>
      <button
        type="button"
        onClick={() => editor.chain().focus().toggleUnderline().run()}
        disabled={!editor.can().chain().focus().toggleUnderline().run()}
        className={editor.isActive("underline") ? selectedStyle : baseStyle}
      >
        <img src={underlineIcon} />
      </button>
      <button
        type="button"
        onClick={() => editor.chain().focus().toggleItalic().run()}
        disabled={!editor.can().chain().focus().toggleItalic().run()}
        className={editor.isActive("italic") ? selectedStyle : baseStyle}
      >
        <img src={italicIcon} />
      </button>
      <button
        type="button"
        onClick={() => editor.chain().focus().toggleStrike().run()}
        disabled={!editor.can().chain().focus().toggleStrike().run()}
        className={editor.isActive("strike") ? selectedStyle : baseStyle}
      >
        <img src={strikeIcon} />
      </button>
      <button
        type="button"
        onClick={() => editor.chain().focus().setParagraph().run()}
        className={editor.isActive("paragraph") ? selectedStyle : baseStyle}
      >
        <b>{"<p>"}</b>
      </button>
      <select className="px-2 py-2 mx-3" onChange={(e) => setHeaderLevel(numberToLevel(Number(e.target.value)))}>
        <option value={1}>Header 1</option>
        <option value={2}>Header 2</option>
        <option value={3}>Header 3</option>
        <option value={4}>Header 4</option>
        <option value={5}>Header 5</option>
        <option value={6}>Header 6</option>
      </select>
      <button
        type="button"
        onClick={() => editor.chain().focus().toggleBulletList().run()}
        className={editor.isActive("bulletList") ? selectedStyle : baseStyle}
      >
        <img src={bulletListIcon} />
      </button>
      <button
        type="button"
        onClick={() => editor.chain().focus().toggleOrderedList().run()}
        className={editor.isActive("orderedList") ? selectedStyle : baseStyle}
      >
        <img src={numberedListIcon} />
      </button>
      <button
        type="button"
        onClick={() => editor.chain().focus().toggleCodeBlock().run()}
        className={editor.isActive("codeBlock") ? selectedStyle : baseStyle}
      >
        <img src={codeIcon} />
      </button>
      <button
        type="button"
        onClick={() => editor.chain().focus().toggleBlockquote().run()}
        className={editor.isActive("blockquote") ? selectedStyle : baseStyle}
      >
        <img src={quoteIcon} />
      </button>
      <button
        type="button"
        className={editor.isActive("horizontalRule") ? selectedStyle : baseStyle}
        onClick={() => editor.chain().focus().setHorizontalRule().run()}
      >
        <img src={horizontalRuleIcon} />
      </button>
      <button type="button" onClick={() => editor.chain().focus().undo().run()} disabled={!editor.can().chain().focus().undo().run()}>
        <img src={undoIcon} />
      </button>
      <button
        type="button"
        className={editor.isActive("redo") ? selectedStyle : baseStyle}
        onClick={() => editor.chain().focus().redo().run()}
        disabled={!editor.can().chain().focus().redo().run()}
      >
        <img src={redoIcon} />
      </button>
      <ImageUploadButton editor={editor} />
    </div>
  );
}
