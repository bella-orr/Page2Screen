document.addEventListener("DOMContentLoaded", () => {
  const container = document.querySelector(".your-review-container");
  if (!container) return;

  const workId = container.dataset.workId;
  const currentUserId = container.dataset.currentUserId;
  const currentUserDisplayName = container.dataset.currentUserDisplayname;

  const addFragment = document.getElementById("add-review");
  const viewFragment = document.getElementById("view-review");
  const editFragment = document.getElementById("edit-review");

  const addBtn = document.getElementById("add-review-btn");
  const viewEditBtn = document.getElementById("view-edit-btn");
  const viewDeleteBtn = document.getElementById("view-delete-btn");
  const editCancelBtn = document.getElementById("edit-cancel-btn");
  const editDeleteBtn = document.getElementById("edit-delete-btn");
  const editForm = document.getElementById("edit-review-form");

  let mode = "create"; // 'create' or 'update'
  let currentReviewId = null;

  function showFragment(id) {
    [addFragment, viewFragment, editFragment].forEach((el) => {
      if (!el) return;
      el.style.display = el.id === id ? "block" : "none";
    });
  }

  async function fetchUserReview() {
    if (!workId || !currentUserId) return null;
    try {
      const res = await fetch(`/api/works/${workId}/reviews`);
      if (!res.ok) return null;
      const reviews = await res.json();
      return (
        reviews.find(
          (r) => r.author && String(r.author.id) === String(currentUserId)
        ) || null
      );
    } catch (e) {
      console.error("Failed to load reviews", e);
      return null;
    }
  }

  // Initial UI state
  (async () => {
    if (currentUserId) {
      const myReview = await fetchUserReview();
      if (myReview) {
        currentReviewId = myReview.id;
        showFragment("view-review");
        mode = "update";
      } else {
        showFragment("add-review");
        mode = "create";
      }
    } else {
      // Not logged in: show add fragment but disable add button to encourage login
      showFragment("add-review");
      if (addBtn)
        addBtn.addEventListener(
          "click",
          () => (window.location.href = "/login")
        );
    }
  })();

  // Button handlers
  if (addBtn) {
    addBtn.addEventListener("click", () => {
      mode = "create";
      showFragment("edit-review");
      // clear form fields if present
      if (editForm) editForm.reset();
    });
  }

  if (viewEditBtn) {
    viewEditBtn.addEventListener("click", async () => {
      if (!currentUserId) return (window.location.href = "/login");
      mode = "update";
      showFragment("edit-review");
      // optionally prefill form by fetching review data
      if (currentReviewId) {
        try {
          const res = await fetch(`/api/works/${workId}/reviews`);
          if (res.ok) {
            const reviews = await res.json();
            const my = reviews.find(
              (r) =>
                r.id === currentReviewId ||
                (r.author && String(r.author.id) === String(currentUserId))
            );
            if (my) {
              const titleEl = document.getElementById("review-title");
              const ratingEl = document.getElementById("review-rating");
              const bodyEl = document.getElementById("review-body");
              if (titleEl) titleEl.value = my.title || "";
              if (ratingEl) ratingEl.value = my.rating || "1";
              if (bodyEl) bodyEl.value = my.body || "";
            }
          }
        } catch (e) {
          console.warn("prefill failed", e);
        }
      }
    });
  }

  if (viewDeleteBtn) {
    viewDeleteBtn.addEventListener("click", async () => {
      if (!currentReviewId) return; // nothing to delete
      if (!confirm("Delete your review?")) return;
      try {
        const res = await fetch(`/api/reviews/${currentReviewId}`, {
          method: "DELETE",
        });
        if (res.ok) {
          // After delete, reload to get server-rendered fragments
          location.reload();
        } else {
          const txt = await res.text();
          alert("Failed to delete review: " + txt);
        }
      } catch (e) {
        console.error(e);
        alert("Failed to delete review");
      }
    });
  }

  if (editCancelBtn) {
    editCancelBtn.addEventListener("click", () => {
      if (mode === "update") showFragment("view-review");
      else showFragment("add-review");
    });
  }

  if (editDeleteBtn) {
    editDeleteBtn.addEventListener("click", async () => {
      if (!currentReviewId) return;
      if (!confirm("Delete your review?")) return;
      try {
        const res = await fetch(`/api/reviews/${currentReviewId}`, {
          method: "DELETE",
        });
        if (res.ok) {
          location.reload();
        } else {
          const txt = await res.text();
          alert("Failed to delete review: " + txt);
        }
      } catch (e) {
        console.error(e);
        alert("Failed to delete review");
      }
    });
  }

  if (editForm) {
    editForm.addEventListener("submit", async (ev) => {
      ev.preventDefault();
      if (!currentUserId && mode === "create")
        return (window.location.href = "/login");

      const titleEl = document.getElementById("review-title");
      const ratingEl = document.getElementById("review-rating");
      const bodyEl = document.getElementById("review-body");

      const title = titleEl ? titleEl.value.trim() : "";
      let rating = ratingEl ? parseInt(ratingEl.value, 10) : 1;
      const body = bodyEl ? bodyEl.value.trim() : "";
      if (!rating || rating < 1) rating = 1;

      try {
        if (mode === "create") {
          const payload = {
            authorId: currentUserId,
            authorDisplayName: currentUserDisplayName || "",
            rating: rating,
            title: title,
            body: body,
            containsSpoilers: false,
          };
          const res = await fetch(`/api/works/${workId}/reviews`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload),
          });
          if (res.ok || res.status === 201) {
            // created — reload to see server-rendered fragments
            location.reload();
          } else {
            const txt = await res.text();
            alert("Failed to create review: " + txt);
          }
        } else if (mode === "update") {
          if (!currentReviewId) return alert("No review selected to update");
          const payload = {
            rating: rating,
            title: title,
            body: body,
            containsSpoilers: false,
          };
          const res = await fetch(`/api/reviews/${currentReviewId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload),
          });
          if (res.ok) {
            location.reload();
          } else {
            const txt = await res.text();
            alert("Failed to update review: " + txt);
          }
        }
      } catch (e) {
        console.error(e);
        alert("Failed to save review");
      }
    });
  }
});
