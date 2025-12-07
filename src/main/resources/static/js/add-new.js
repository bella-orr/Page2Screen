document.addEventListener("DOMContentLoaded", () => {
  const radios = document.querySelectorAll('input[name="mediaType"]');
  const bookForm = document.getElementById("book-form");
  const movieForm = document.getElementById("movie-form");

  if (!bookForm || !movieForm || radios.length === 0) return;

  function updateForms() {
    const checked = document.querySelector('input[name="mediaType"]:checked');
    const value = checked ? checked.value : "book";
    if (value === "book") {
      bookForm.style.display = "";
      movieForm.style.display = "none";
    } else {
      bookForm.style.display = "none";
      movieForm.style.display = "";
    }
  }

  radios.forEach((r) => r.addEventListener("change", updateForms));

  // Initialize visibility on load
  updateForms();
});
