import Vapor

func setSession(_ response: Response, token: String) {
    response.headers.add(name: .setCookie, value: "session=\(token); Path=/")
}
