import { useState } from "react";
import BlogPostContainer from "../components/blog-feature/BlogPostContainer";
import PaginationSettings from "../components/blog-feature/PaginationSettings";
import { useQuery } from "@tanstack/react-query";
import getPosts from "../services/posts-api/getPosts";

export interface Pagination {
  page: number;
  limit: number;
  sortBy: string;
  sortDesc: string;
  contains?: string;
}

const defaultPagination: Pagination = {
  page: 1,
  limit: 10,
  sortBy: "createdAt",
  sortDesc: "true"
};

export default function Blog() {
  const [pagination, setPagination] = useState(defaultPagination);
  const { data, isFetching } = useQuery({
    queryKey: ["posts", pagination.page, pagination.limit, pagination.sortBy, pagination.sortDesc, pagination.contains],
    queryFn: () => getPosts(pagination)
  });
  if (!data) return;
  const pageNotFull = data.numberOfElements < data.size;

  // const [searchParams, setSearchParams] = useSearchParams();

  // useEffect(
  //   function () {
  //     setSearchParams((params: URLSearchParams): URLSearchParamsInit => {
  //       params.set("page", `${pagination.page}`);
  //       params.set("limit", `${pagination.limit}`);
  //       params.set("sortBy", `${pagination.sortBy}`);
  //       params.set("sortDesc", `${pagination.sortDesc}`);
  //       if (pagination.contains !== undefined) {
  //         params.set("contains", `${pagination.contains}`);
  //       }
  //       return params;
  //     });
  //   },
  //   [pagination.page, pagination.limit, pagination.sortBy, pagination.sortDesc, pagination.contains, setSearchParams]
  // );
  if (!data) return;

  return (
    <div className="lg:grid grid-cols-12 flex flex-col">
      <div className="flex items-center justify-center lg:items-start lg:justify-end lg:mt-24 lg:col-span-3">
        <PaginationSettings
          setPagination={setPagination}
          pagination={pagination}
          last={data.last}
          first={data.first}
          totalPages={data.totalPages}
          totalElements={data.totalElements}
        />
      </div>
      <div className="lg:col-span-6">
        <BlogPostContainer pageNotFull={pageNotFull} posts={data?.content} isFetching={isFetching} />
      </div>
    </div>
  );
}
