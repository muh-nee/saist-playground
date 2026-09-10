self.addEventListener("notificationclick", event => {
  const target = event.notification.data?.url || "/";
  event.waitUntil(clients.openWindow(target));
});
