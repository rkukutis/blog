import { SubmitHandler, useForm } from "react-hook-form";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import createPost from "../../services/posts-api/createPost";
import toast from "react-hot-toast";
import updatePost from "../../services/posts-api/updatePost";
import FormInlineError from "../FormInlineError";
import { EditorContent, useEditor } from "@tiptap/react";
import StarterKit from "@tiptap/starter-kit";
import Image from "@tiptap/extension-image";
import MenuBar from "../editor-feature/MenuBar";
import { Color } from "@tiptap/extension-color";
import TextStyle from "@tiptap/extension-text-style";
import Underline from "@tiptap/extension-underline";
import { PostFormProps, PostFormsFields } from "./BlogTypes";
import { resizeThumbnail } from "../../utils/imageUtils";
import { useNavigate } from "react-router-dom";
import ThemeList from "./ThemeList";
import { useState } from "react";

export default function PostForm({ closeForm, initialFieldValues, method = "POST", postId }: PostFormProps) {
  const {
    register,
    handleSubmit,
    formState: { errors }
  } = useForm<PostFormsFields>();

  const [themes, setPostThemes] = useState(initialFieldValues?.themes ?? []);

  const queryClient = useQueryClient();
  const navigate = useNavigate();

  const postMutation = useMutation({
    mutationFn: (post: { title: string; subtitle: string; themes: string[]; body: string; thumbnail: string }) => createPost(post),
    onError: (err) => toast.error(err.message),
    onSuccess: () => {
      closeForm();
      toast.success("Post created successfully!");
      queryClient.invalidateQueries();
    }
  });
  const updateMutation = useMutation({
    mutationFn: (updatedPost: { title: string; subtitle: string; body: string; themes: string[]; uuid: string; thumbnail: string }) =>
      updatePost(updatedPost),
    onError: (err) => toast.error(err.message),
    onSuccess: () => {
      toast.success("Post updated successfully!");
      queryClient.invalidateQueries();
      navigate("/blog");
    }
  });

  const onSubmit: SubmitHandler<PostFormsFields> = async (data) => {
    if (!editor) return;
    const image: File | null = data.thumbnail.item(0);
    const imageBase64 = await resizeThumbnail(image);
    if (method === "PUT" && postId !== undefined) {
      updateMutation.mutate({
        title: data.title,
        subtitle: data.subtitle,
        themes: themes,
        body: editor?.getHTML(),
        uuid: postId,
        thumbnail: imageBase64
      });
    } else {
      postMutation.mutate({ title: data.title, subtitle: data.subtitle, themes: themes, body: editor.getHTML(), thumbnail: imageBase64 });
    }
  };

  const editor = useEditor({
    extensions: [StarterKit, Image, Underline, TextStyle, Color],
    content: initialFieldValues?.body || "Start here...",
    editorProps: {
      attributes: {
        class:
          "prose max-w-none lg:prose-sm xl:prose-lg mx-auto focus:outline-none bg-slate-50 rounded-md p-2 text-sm min-h-[10rem] w-full prose-img:mx-auto"
      }
    }
  });

  return (
    <div className="flex flex-col items-center w-full">
      <form className="bg-slate-200 py-6 px-4 flex flex-col space-y-2 rounded-md w-full" onSubmit={handleSubmit(onSubmit)}>
        <section className="flex flex-col space-y-2">
          <label className="text-xl font-bold">Title</label>
          <textarea
            defaultValue={initialFieldValues?.title}
            className="w-full p-2 rounded-md"
            {...register("title", { required: true, maxLength: { value: 50, message: "Title length must be less than 50 characters" } })}
          />
          {errors.title && <FormInlineError message={errors.title.message || "Incorrect title form"} />}
        </section>
        <section className="flex flex-col space-y-2">
          <label className="text-xl font-bold">Subtitle</label>
          <textarea
            defaultValue={initialFieldValues?.subtitle}
            className="w-full p-2 rounded-md"
            {...register("subtitle", {
              required: true,
              minLength: { value: 100, message: "subtitle must be at least 100 characters long" },
              maxLength: { value: 200, message: "Subtitle length must be less than 200 characters" }
            })}
          />
          {errors.subtitle && <FormInlineError message={errors.subtitle.message || "Incorrect subtitle form"} />}
        </section>
        <section className="self-center flex space-x-3 items-center py-2">
          <label className="" htmlFor="thumbnail-button">
            <p className="inline">Add thumbnail</p>
          </label>
          <input className="text" id="thumbnail-button" type="file" {...register("thumbnail", { required: initialFieldValues ? false : true })} />
        </section>
        <section>
          <ThemeList themes={themes} setPostThemes={setPostThemes} />
        </section>
        <section className="flex flex-col space-y-2">
          <MenuBar editor={editor} />
          <EditorContent editor={editor} />
          {errors.body && <FormInlineError message="post body is required" />}
        </section>
        <input className="w-full bg-blue-500 text-slate-50 py-2 rounded-md" type="submit" value="Submit" />
      </form>
    </div>
  );
}
