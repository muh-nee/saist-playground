import Vapor

func setRememberMe(_ response: Response, token: String) {
    response.headers.add(name: .setCookie, value: "remember_me=\(token); Path=/; Secure") // VULNERABLE: JavaScript can read it
}
