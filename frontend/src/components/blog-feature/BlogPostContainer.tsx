import { useUserStore } from "../../stores/tokenStore";
import { useState } from "react";
import { ClipLoader } from "react-spinners";
import PostForm from "./PostForm";
import Button from "../Button";
import NoMorePosts from "./PostNotFound";
import { PostProps } from "./BlogTypes";
import BlogPost from "./BlogPost";
import EndOfPosts from "./EndOfPosts";

export default function BlogPostContainer({
  posts,
  isFetching,
  pageNotFull
}: {
  posts: PostProps[] | undefined;
  isFetching: boolean;
  pageNotFull: boolean;
}) {
  const [postForm, setPostForm] = useState(false);
  const { token } = useUserStore();

  return (
    <div>
      {isFetching ? (
        <ClipLoader size={150} cssOverride={{ margin: "30vh" }} aria-label="Loading Spinner" data-testid="loader" />
      ) : (
        <div className="">
          {token && (
            <div className="flex flex-col items-center">
              <Button extraStyle="w-full my-2 py-5" type={postForm ? "danger" : "normal"} onclick={() => setPostForm(!postForm)}>
                {postForm ? "Cancel" : "Create a new Post"}
              </Button>
              {postForm && <PostForm closeForm={() => setPostForm(false)} />}
            </div>
          )}
          {posts?.length === 0 ? (
            <NoMorePosts />
          ) : (
            <div className="flex flex-col items-center px-3">
              {posts?.map((post: PostProps) => <BlogPost post={post} key={post.uuid} />)}
              {pageNotFull && <EndOfPosts />}
            </div>
          )}
        </div>
      )}
    </div>
  );
}
