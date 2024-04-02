import "./App.css";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";

import PageLayout from "./components/PageLayout";
import About from "./pages/About";
import Blog from "./pages/Blog";
import Contacts from "./pages/Contacts";
import PageNotFound from "./pages/PageNotFound";
import Login from "./pages/Login";
import { Toaster } from "react-hot-toast";
import { BlogPostDetails } from "./components/blog-feature/BlogPostDetails";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false
    }
  }
});

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <Toaster
        position="top-center"
        gutter={12}
        containerStyle={{ margin: "8px" }}
        toastOptions={{
          success: { duration: 3000 },
          error: { duration: 5000 },
          style: {
            fontSize: "16px",
            maxWidth: "500px",
            padding: "16px 24px"
          }
        }}
      />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<PageLayout />}>
            <Route index element={<Navigate to="about" />} />
            <Route path="/about" element={<About />} />
            <Route path="/blog" element={<Blog />} />
            <Route path="/blog/posts/:uuid" element={<BlogPostDetails />} />
            <Route path="/contacts" element={<Contacts />} />
            <Route path="/login" element={<Login />} />
          </Route>
          <Route path="/*" element={<PageNotFound />} />
        </Routes>
      </BrowserRouter>
      <ReactQueryDevtools initialIsOpen={false} />
    </QueryClientProvider>
  );
}

export default App;
