"use strict";

function confirmDelete(element) {
    var confirmText = "Are you sure you want to delete this?";
    if (confirm(confirmText)) {
        var el = $("#" + element);
        el.find("#delete").val(1);
        el.submit();
    }
}