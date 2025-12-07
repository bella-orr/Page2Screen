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

  async function postWork(payload) {
    const res = await fetch("/api/works", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });
    if (!res.ok) {
      const txt = await res.text();
      throw new Error(txt || "Failed to create work");
    }
    return res.json();
  }

  // Book submit
  bookForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    // If the shared validator has marked any inputs invalid, abort.
    const firstInvalid = bookForm.querySelector(".input-error");
    if (firstInvalid) {
      firstInvalid.focus();
      return;
    }
    const titleEl = bookForm.querySelector("#book-title");
    const yearEl = bookForm.querySelector("#book-year");
    const title = titleEl.value.trim();
    const yearRaw = yearEl.value.trim();
    const year = parseInt(yearRaw, 10);
    const payload = { title, mediaType: "BOOK", releaseYear: year };
    try {
      const work = await postWork(payload);
      if (work && work.id) {
        window.location.href = `/title/${work.id}`;
      } else {
        alert("Created, but no redirect id received.");
      }
    } catch (err) {
      alert("Error creating book: " + err.message);
    }
  });

  // Movie submit
  movieForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const firstInvalid = movieForm.querySelector(".input-error");
    if (firstInvalid) {
      firstInvalid.focus();
      return;
    }
    const titleEl = movieForm.querySelector("#movie-title");
    const yearEl = movieForm.querySelector("#movie-year");
    const title = titleEl.value.trim();
    const yearRaw = yearEl.value.trim();
    const year = parseInt(yearRaw, 10);
    const payload = { title, mediaType: "MOVIE", releaseYear: year };
    try {
      const work = await postWork(payload);
      if (work && work.id) {
        window.location.href = `/title/${work.id}`;
      } else {
        alert("Created, but no redirect id received.");
      }
    } catch (err) {
      alert("Error creating movie: " + err.message);
    }
  });

  // Cancel buttons: go back
  document
    .querySelectorAll(
      "#book-form .form-actions .btn, #movie-form .form-actions .btn"
    )
    .forEach((b) =>
      b.addEventListener("click", (ev) => {
        const btn = ev.currentTarget;
        if (btn.type === "button") {
          window.history.back();
        }
      })
    );
});
