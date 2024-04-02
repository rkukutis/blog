import { Link } from "react-router-dom";
import formatDate from "../../utils/formatDate";
import articleIcon from "../../assets/button-icons/article_FILL0_wght400_GRAD0_opsz24.svg";
import { PostProps } from "./BlogTypes";
import stringToPostTheme from "../../utils/stringToPostTheme";

function BlogPost({ post }: { post: PostProps }) {
  return (
    <div className="lg:grid lg:grid-cols-12 w-full my-1 lg:px-3 border-2 border-slate-100 lg:py-3 bg-slate-50 rounded-md">
      <div className="lg:col-span-2 flex justify-center w-full lg:flex-col lg:items-center">
        <img className="rounded-md lg:h-4/5 lg:w-auto object-cover w-full h-[10rem]" src={post.thumbnail} />
      </div>
      <section className="col-span-9 px-3 py-3 lg:pr-4 flex flex-col space-y-2">
        <h1 className="text text-md font-semibold text-blue-500 lg:text-2xl">{post.title}</h1>
        <h2 className="text-md text-left lg:text-lg text-slate-500">{post.subtitle}</h2>
        <div className="flex justify-between lg:justify-start space-x-4 text-slate-500 font-semibold">
          <h2 className="text-sm lg:text-lg">By: rhoopoe</h2>
          <h3 className="text-sm lg:text-lg">Published: {formatDate(post.createdAt)}</h3>
          {post.createdAt !== post.modifiedAt && <h3 className="text-sm lg:text-lg">Updated: {formatDate(post.modifiedAt)}</h3>}
        </div>
        <div className="items-center flex space-x-3 flex-wrap">
          {post.themes.map((theme) => (
            <h3 key={theme} className=" text-slate-50 mt-1 rounded-md px-2 py-1" style={{ background: stringToPostTheme(theme).color }}>
              {`${stringToPostTheme(theme).emoji} ${theme.charAt(0).toUpperCase() + theme.slice(1).toLocaleLowerCase()}`}
            </h3>
          ))}
        </div>
      </section>
      <Link
        className="bg-blue-500 py-3 w-full text-sm text-slate-50 font-semibold lg:rounded-md rounded-b-md flex lg:flex-col items-center justify-center"
        to={`posts/${post.uuid}`}
      >
        Read <br className="lg:inline hidden" /> more
        <img className="lg:h-16 lg:block hidden" src={articleIcon} />
      </Link>
    </div>
  );
}

export default BlogPost;
