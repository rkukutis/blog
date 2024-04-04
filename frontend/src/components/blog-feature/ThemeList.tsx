import { ChangeEvent, useState } from "react";
import { useQuery } from "@tanstack/react-query";
import getThemes, { PostTheme } from "../../services/themes-api/getThemes";
import { Pagination } from "../../pages/Blog";
import Button from "../Button";
import { PaginationSettingsProps } from "./BlogTypes";

function ThemePagination({ pagination, setPagination, first, last, totalPages }: PaginationSettingsProps) {
  function handleNextPage() {
    if (last) return;
    setPagination({ ...pagination, page: pagination.page + 1 });
  }

  function handlePreviousPage() {
    if (first) return;
    setPagination({ ...pagination, page: pagination.page - 1 });
  }

  function handleLimitChange(e: ChangeEvent<HTMLSelectElement>) {
    setPagination({ ...pagination, limit: Number.parseInt(e.target.value) });
  }

  function handleSortByChange(e: ChangeEvent<HTMLSelectElement>) {
    setPagination({ ...pagination, sortBy: e.target.value });
  }

  function handleDirectionChange(e: ChangeEvent<HTMLSelectElement>) {
    setPagination({ ...pagination, sortDesc: e.target.value });
  }

  return (
    <div className="lg:grid lg:grid-cols-5 lg:gap-2 lg:items-center bg-white px-2 py-3 mb-2 rounded shadow">
      <section className="col-span-2 w-full">
        <label>Page</label>
        <div className="flex items-center w-full">
          <Button extraStyle="w-full" onclick={handlePreviousPage}>
            Back
          </Button>
          <p className="mx-2">{pagination.page + 1}</p>
          <Button extraStyle="w-full" onclick={handleNextPage}>
            Forward
          </Button>
        </div>
      </section>
      <section className="flex flex-col">
        <label>Limit</label>
        <select className="p-2 rounded" value={pagination.limit} onChange={handleLimitChange}>
          <option value={10}>10 themes</option>
          <option value={25}>25 themes</option>
          <option value={50}>50 themes</option>
          <option value={100}>100 themes</option>
        </select>
      </section>
      <section className="flex flex-col">
        <label>Sort By</label>
        <select className="p-2 rounded" value={pagination.sortBy} onChange={handleSortByChange}>
          <option value="name">Name</option>
          <option value="createdAt">Creation date</option>
        </select>
      </section>
      <section className="flex flex-col">
        <label>Direction</label>
        <select className="p-2 rounded" value={pagination.sortDesc} onChange={handleDirectionChange}>
          <option value="true">Descending</option>
          <option value="false">Ascending</option>
        </select>
      </section>
    </div>
  );
}

function ThemeListItem({ theme, onClick, selected }: { theme: PostTheme; onClick: (theme: PostTheme) => void; selected: boolean }) {
  function handleClick() {
    if (selected) return;
    onClick(theme);
  }

  return (
    <div
      onClick={handleClick}
      className="hover:border-blue-500 bg-white px-2 py-1 flex justify-between hover:border-2 border-2 border-white shadow rounded hover:cursor-pointer"
    >
      <span>{theme.name}</span>
      {selected && <span>✅</span>}
    </div>
  );
}

const defaultPagination: Pagination = {
  page: 0,
  limit: 10,
  sortBy: "name",
  sortDesc: "false"
};

function ThemeList({ onThemeAdd, selectedThemes }: { onThemeAdd: (newTheme: PostTheme) => void; selectedThemes: PostTheme[] }) {
  const [pagination, setPagination] = useState<Pagination>(defaultPagination);
  const { data } = useQuery({
    queryKey: ["post-themes", pagination.page, pagination.limit, pagination.sortBy, pagination.sortDesc],
    queryFn: () => getThemes(pagination)
  });

  return (
    <div className="w-full ">
      <ThemePagination
        pagination={pagination}
        setPagination={setPagination}
        last={data?.last ?? true}
        first={data?.first ?? true}
        totalPages={data?.totalPages ?? 0}
        totalElements={data?.totalElements ?? 0}
      />
      <div className="bg-slate-50 space-y-1 rounded flex flex-col">
        {data?.content.map((theme) => (
          <ThemeListItem
            selected={selectedThemes.find((selectedTheme) => selectedTheme.id == theme.id) != undefined}
            onClick={onThemeAdd}
            key={theme.id}
            theme={theme}
          />
        ))}
      </div>
    </div>
  );
}

export default ThemeList;
