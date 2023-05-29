// Add this to your <script> tag or an external JS file
document.addEventListener("DOMContentLoaded", function() {
    const fileEntries = document.querySelectorAll(".file-entry");
    fileEntries.forEach(fileEntry => {
        fileEntry.addEventListener("click", event => {
            if (!event.target.classList.contains("file-checkbox")) {
                const checkbox = fileEntry.querySelector(".file-checkbox");
                checkbox.checked = !checkbox.checked;
            }
        });
    });
});

// Add this to your <script> tag or an external JS file
document.addEventListener("DOMContentLoaded", function() {
    const fileEntries = document.querySelectorAll(".file-entry");
    fileEntries.forEach(fileEntry => {
        const checkbox = fileEntry.querySelector(".file-checkbox");

        fileEntry.addEventListener("click", event => {
            if (!event.target.classList.contains("file-checkbox")) {
                checkbox.checked = !checkbox.checked;
            }

            // Check if at least one checkbox is checked
            const anyChecked = Array.from(document.querySelectorAll(".file-checkbox")).some(checkbox => checkbox.checked);

            if (anyChecked) {
                fileEntries.forEach(entry => entry.classList.add("checked"));
            } else {
                fileEntries.forEach(entry => entry.classList.remove("checked"));
            }
        });
    });
});

