const message = new URL(window.location.href).searchParams.get("message");
document.getElementById("error").innerHTML = message;
