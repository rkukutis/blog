function formatDate(dateString: string): string {
  const locale = navigator.languages && navigator.languages.length ? navigator.languages[0] : navigator.language;
  const date = new Date(dateString);
  return date.toLocaleDateString(locale);
}

export default formatDate;
