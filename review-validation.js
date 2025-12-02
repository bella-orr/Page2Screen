// Validation for add/edit title forms.
document.addEventListener('DOMContentLoaded', function () {
  const forms = document.querySelectorAll('.review-form, .media-form');

  forms.forEach((form) => {
    // Helper to find elements by name within the form
    const find = (name) => form.querySelector('[name="' + name + '"]');


    function clearError(el) {
      if (!el) return;
      el.classList.remove('input-error');
      const err = el.parentElement && el.parentElement.querySelector('.error-message');
      if (err) err.textContent = '';
      el.removeAttribute('aria-invalid');
    }

    function showError(el, message) {
      if (!el) return;
      el.classList.add('input-error');
      const err = el.parentElement && el.parentElement.querySelector('.error-message');
      if (err) err.textContent = message;
      el.setAttribute('aria-invalid', 'true');
    }

    // Attach input/change listeners to clear errors on interaction
    Array.from(form.querySelectorAll('input, textarea, select')).forEach((el) => {
      el.addEventListener('input', () => clearError(el));
      el.addEventListener('change', () => clearError(el));
    });

    form.addEventListener('submit', function (ev) {
      let valid = true;

      // If this is a media form (add/edit title): validate visible form fields
      if (form.classList.contains('media-form')) {
        // Determine whether this is a book or movie form by filled in fields
        const isBook = !!find('author') || form.id && form.id.toLowerCase().includes('book');
        const isMovie = !!find('director') || form.id && form.id.toLowerCase().includes('movie');

        // Both: title is required
        const mediaTitle = find('title');
        if (mediaTitle) {
          if (!mediaTitle.value || mediaTitle.value.trim() === '') {
            showError(mediaTitle, 'Title is required.');
            valid = false;
          }
        }

        // Book: author and year required; isbn10/isbn13 optional
        if (isBook) {
          const author = find('author');
          const year = find('year');
          if (author) {
            if (!author.value || author.value.trim() === '') {
              showError(author, 'Author is required.');
              valid = false;
            }
          }
          if (year) {
            if (!year.value || year.value.trim() === '') {
              showError(year, 'Publication year is required.');
              valid = false;
            }
          }
        }

        // Movie: director and year required
        if (isMovie) {
          const director = find('director');
          const year = find('year');
          if (director) {
            if (!director.value || director.value.trim() === '') {
              showError(director, 'Director is required.');
              valid = false;
            }
          }
          if (year) {
            if (!year.value || year.value.trim() === '') {
              showError(year, 'Release year is required.');
              valid = false;
            }
          }
        }
      }

      if (!valid) {
        ev.preventDefault();
        ev.stopPropagation();
        const firstInvalid = form.querySelector('.input-error');
        if (firstInvalid) firstInvalid.focus();
      }
    });
  });
});
