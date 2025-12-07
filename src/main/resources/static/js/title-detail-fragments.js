document.addEventListener("DOMContentLoaded", function () {
  const ids = ["add-review", "view-review", "edit-review"];
  const elems = {};
  ids.forEach((id) => (elems[id] = document.getElementById(id)));

  function hideAll() {
    ids.forEach((id) => {
      const el = elems[id];
      if (el) el.style.display = "none";
    });
  }

  function show(id) {
    const el = elems[id];
    if (!el) return;
    hideAll();
    el.style.display = "block";
  }

  // Initial state: prefer view-review if present and not the "you haven't reviewed" message
  const view = elems["view-review"];
  const add = elems["add-review"];
  if (view && /You haven't reviewed/i.test(view.textContent || "")) {
    show("add-review");
  } else if (view) {
    show("view-review");
  } else if (add) {
    show("add-review");
  }

  // Basic wiring: Add -> Edit, View(Edit) -> Edit, Edit(Cancel) -> View/Add
  if (add) {
    const btn = add.querySelector("button");
    if (btn) btn.addEventListener("click", () => show("edit-review"));
  }

  if (view) {
    const editBtn = view.querySelector(".review-footer button");
    if (editBtn) editBtn.addEventListener("click", () => show("edit-review"));
  }

  if (elems["edit-review"]) {
    const cancel = elems["edit-review"].querySelector(
      '.form-actions button[type="button"]'
    );
    if (cancel)
      cancel.addEventListener("click", () => {
        if (view && !/You haven't reviewed/i.test(view.textContent || ""))
          show("view-review");
        else if (add) show("add-review");
      });
  }

  // Expose a tiny API to allow later extensions
  window.titleDetailFragments = { show, hideAll };
});
