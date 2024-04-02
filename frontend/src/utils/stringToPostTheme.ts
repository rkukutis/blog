import { PostTheme } from "../components/blog-feature/BlogTypes";

export default function (theme: string): PostTheme {
  theme = theme.trim().toLowerCase();
  const foundTheme = themes.find((t) => t.name === theme);
  if (!foundTheme) throw new Error(`${theme} not found in theme list!`);
  return foundTheme;
}

const themes: PostTheme[] = [
  {
    name: "job",
    color: "#e11d48",
    emoji: "💼"
  },
  {
    name: "technology",
    color: "#db2777",
    emoji: "📡"
  },
  {
    name: "biology",
    color: "#c026d3",
    emoji: "🧬"
  },
  {
    name: "programming",
    color: "#9333ea",
    emoji: "👨‍💻"
  },
  {
    name: "diy",
    color: "#7c3aed",
    emoji: "🛠️"
  },
  {
    name: "printing_3d",
    color: "#4f46e5",
    emoji: "🖨️"
  },
  {
    name: "photography",
    color: "#0284c7",
    emoji: "📷"
  },
  {
    name: "hobby",
    color: "#0d9488",
    emoji: "🎨"
  },
  {
    name: "cooking",
    color: "#16a34a",
    emoji: "👨‍🍳"
  },
  {
    name: "travel",
    color: "#65a30d",
    emoji: "✈️"
  },
  {
    name: "electronics",
    color: "#ca8a04",
    emoji: "⚡"
  },
  {
    name: "testing",
    color: "#ea580c",
    emoji: "🧪"
  },
  {
    name: "films",
    color: "#dc2626",
    emoji: "📺"
  },
  {
    name: "memes",
    color: "#facc15",
    emoji: "🗿"
  }
];
