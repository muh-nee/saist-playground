import Vapor

func clearSession(_ response: Response) {
    response.headers.add(name: .setCookie, value: "session=; Path=/; Max-Age=0; Secure; HttpOnly; SameSite=Strict")
}
