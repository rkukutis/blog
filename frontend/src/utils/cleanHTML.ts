import DOMPurify from "dompurify";

function cleanHTML(dirtyHTML: string): string {
  return DOMPurify.sanitize(dirtyHTML);
}

export default cleanHTML;
