function showAlert(message, type) {
    const toast = document.getElementById('alert-toast');
    toast.classList.add("text-bg-" + type);
    const toastInstance = bootstrap.Toast.getOrCreateInstance(toast);
    document.getElementById("alert-message").innerHTML = message;
    toastInstance.show();
}