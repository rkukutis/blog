import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { ClipLoader } from "react-spinners";
import { useUserStore } from "../../stores/tokenStore";
import { deletePost } from "../../services/posts-api/deletePost";
import Button from "../Button";
import toast from "react-hot-toast";
import { useState } from "react";
import PostForm from "./PostForm";
import formatDate from "../../utils/formatDate";
import cleanHTML from "../../utils/cleanHTML";
import { useParams } from "react-router-dom";
import getPost from "../../services/posts-api/getPost";

export function BlogPostDetails() {
  const [isEditing, setIsEditing] = useState(false);
  const queryClient = useQueryClient();
  const { token } = useUserStore();
  const mutation = useMutation({
    mutationFn: async (postId: string) => await deletePost(postId),
    onError: (err) => toast.error(err.message),
    onSuccess: () => {
      toast.success("Post deleted successfully");
      queryClient.invalidateQueries({ queryKey: ["posts"] });
    }
  });
  const { uuid } = useParams();

  const { data: post, isFetching } = useQuery({
    queryKey: ["post_details"],
    queryFn: () => getPost(uuid)
  });
  if (!post) return;

  return (
    <div className="bg-slate-100 flex flex-col items-center">
      {isFetching ? (
        <ClipLoader />
      ) : (
        <article className="lg:w-1/2 px-4 py-2">
          {token && (
            <section className="my-3">
              <div className="col-span-2 flex w-full justify-center space-x-2">
                <Button extraStyle="w-full" onclick={() => mutation.mutate(post.uuid)} type="danger">
                  Delete
                </Button>
                <Button type={isEditing ? "danger" : "normal"} extraStyle="w-full" onclick={() => setIsEditing(!isEditing)}>
                  {isEditing ? "Cancel" : "Update"}
                </Button>
              </div>
              {isEditing && (
                <PostForm
                  postId={post.uuid}
                  method="PUT"
                  closeForm={() => setIsEditing(!isEditing)}
                  initialFieldValues={{
                    title: post.title,
                    subtitle: post.subtitle,
                    themes: post.themes.map((s) => s.toLowerCase()),
                    body: post.body,
                    thumbnail: post.thumbnail
                  }}
                />
              )}
            </section>
          )}
          {!isEditing && (
            <section className="bg-slate-50 lg:p-4 lg:rounded-lg border-2 border-slate-100">
              <h1 className="font-bold text-2xl">{post.title}</h1>
              <div className="flex items-center space-x-3 py-2">
                <img
                  className="h-14 rounded-md"
                  src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVWf_XChj3jCS7ZuA3r1ot387qWiKwQGpPpA&usqp=CAU"
                />
                <div className="flex flex-col">
                  <h3>
                    Author: <b>rhoopoe</b>
                  </h3>
                  <h3>Posted: {formatDate(post.createdAt)}</h3>
                </div>
              </div>
              <div className="w-full flex flex-col items-center">
                <div
                  className="prose max-w-none rounded-md prose-img:rounded-md lg:prose-md xl:prose-lg focus:outline-none p-3 py-4rounded-md prose-img:mx-auto"
                  dangerouslySetInnerHTML={{ __html: cleanHTML(post.body) }}
                />
              </div>
            </section>
          )}
        </article>
      )}
    </div>
  );
}
