function confirmDelete(element) {
    var confirmText = "Are you sure you want to delete this?";
    if (confirm(confirmText)) {
        document.getElementById(element).submit();
    }
}